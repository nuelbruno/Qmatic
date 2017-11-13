package ae.qmatic.tacme.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.db.DatabaseHandler;
import ae.qmatic.tacme.model.DeleteTicket;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.RemoteBookingBranchByIDModel;
import ae.qmatic.tacme.model.RemoteBookingBranchesModel;
import ae.qmatic.tacme.model.RemoteBookingIssueTicketModel;
import ae.qmatic.tacme.model.RemoteBookingServicesModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.GPSTracker;
import ae.qmatic.tacme.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRemoteBooking extends AppCompatActivity implements DialogUtils.DialogListner {

    LinearLayout selectBranchDropDown, selectServiceDropDown, selectAvilableDatesDropDown;
    TextView selectedBranchTextView, selectedServiceTextView, selectedAvilableDatedTextView;
    TextView txtviewErrorBranchSelection, txtviewErrorServiceSelection, txtviewErrorDateSelection;
    Button buttonNext;
    private String[] mExpectedTimeArray;
    GPSTracker gps;
    double currentLatitude, currentLongitude;
    private String mSelectedServiceId = "", mSelectedBranchId = "", mSelectedDelay = "";
    private String mSelectedServiceName = "", mSelectedBranchName = "", mSelectedDelayTime = "", mExtendedTime = "", mVisitid = "";
    private String[] DelayArray = {"5", "10", "15", "20"};
    ActivityConstant activityConstant;
    LinearLayout mButtonLayout, mTicketLayout, mContactinfoLayout, mCancelLayout;
    RelativeLayout mWaitQueueLayout, mAverageTimeLayout;
    TextView mTicketNumber;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_remote_booking);
        db = new DatabaseHandler(this);
        activityConstant = new ActivityConstant();
        mExpectedTimeArray = getResources().getStringArray(R.array.expectedTimeList);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        ImageView imgRightIcon = (ImageView) toolbarTop.findViewById(R.id.imgRightIcon);
        imgRightIcon.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.remoteBooking));

        initializeUI();
        setListeners();

        if (NetworkUtils.getInstance(this).isOnline()) {
            getCurrentLocation();
        } else
            Toast.makeText(MyRemoteBooking.this, "Please connect to internet.", Toast.LENGTH_SHORT).show();

    }

    public void initializeUI() {
        // Next green color button
        buttonNext = (Button) findViewById(R.id.buttonNext);
        selectBranchDropDown = (LinearLayout) findViewById(R.id.selectBranchDropDown);
        selectServiceDropDown = (LinearLayout) findViewById(R.id.selectServiceDropDown);
        selectAvilableDatesDropDown = (LinearLayout) findViewById(R.id.selectAvilableDatesDropDown);
        selectedBranchTextView = (TextView) findViewById(R.id.selectedBranchTextView);
        selectedServiceTextView = (TextView) findViewById(R.id.selectedServiceTextView);
        selectedAvilableDatedTextView = (TextView) findViewById(R.id.selectedAvilableDatedTextView);
        ///Error messages
        txtviewErrorBranchSelection = (TextView) findViewById(R.id.txtviewErrorBranchSelection);
        txtviewErrorServiceSelection = (TextView) findViewById(R.id.txtviewErrorServiceSelection);
        txtviewErrorDateSelection = (TextView) findViewById(R.id.txtviewErrorDateSelection);
    }

    public void setListeners() {
        selectBranchDropDown.setOnClickListener(clickListeners);
        selectServiceDropDown.setOnClickListener(clickListeners);
        selectedAvilableDatedTextView.setOnClickListener(clickListeners);
        buttonNext.setOnClickListener(clickListeners);
    }

    View.OnClickListener clickListeners = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.selectBranchDropDown:
                    if (selectedServiceTextView.getText().toString().trim().length() > 0) {
                        getBranches(mSelectedServiceId);
                    } else {
                        DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.selectService), MyRemoteBooking.this);
                    }
                    break;
                case R.id.selectServiceDropDown:
                    getServices();
                    break;
                case R.id.selectedAvilableDatedTextView:
                    if ((selectedServiceTextView.getText().toString().trim().length() > 0) && (selectedBranchTextView.getText().toString().trim().length() > 0)) {
                        List<DialogItem> mList = new ArrayList<DialogItem>();
                        for (int i = 0; i < mExpectedTimeArray.length; i++) {
                            DialogItem dialogItem = new DialogItem();
                            dialogItem.setName(mExpectedTimeArray[i]);
                            dialogItem.setId(DelayArray[i]);
                            mList.add(dialogItem);
                        }
                        DialogUtils.dialogList(MyRemoteBooking.this, getResources().getString(R.string.selectTime), mList, MyRemoteBooking.this, ActivityConstant.EXPECTEDTIME_CODE);
                    } else {
                        DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.selectBranchs), MyRemoteBooking.this);
                    }
                    break;

                case R.id.buttonNext:
                    if (isValidationSuccess())
                        getBranchDetailsByID();
                    break;
            }
        }
    };

    public void getBranches(String mSerivceId) {
        activityConstant.showProgressDialog(this, "wait...");
        final List<DialogItem> mList = new ArrayList<DialogItem>();
        ServiceManager ss = new ServiceManager();
        Call<List<RemoteBookingBranchesModel>> call = ss.apiService.getAllRemoteBookingBranchesBasedonServiceId(mSerivceId, "0", "0", "10000000");
        call.enqueue(new Callback<List<RemoteBookingBranchesModel>>() {
            @Override
            public void onResponse(Call<List<RemoteBookingBranchesModel>> call, Response<List<RemoteBookingBranchesModel>> response) {
                Log.d("Length: ", "BRANCH: " + response.body().size(), null);
                if (response.body().size() > 0 && response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("Branch Name   ===>  ", "" + response.body().get(i).getName());
                        DialogItem dialogItem = new DialogItem();
                        dialogItem.setId("" + response.body().get(i).getId());
                        dialogItem.setName(response.body().get(i).getName());
                        mList.add(dialogItem);
                    }
                    DialogUtils.dialogList(MyRemoteBooking.this, getResources().getString(R.string.selectBranch), mList, MyRemoteBooking.this, ActivityConstant.BRANCH_CODE);
                } else {
                    DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.noBranchesFound), MyRemoteBooking.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<RemoteBookingBranchesModel>> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.webservice_error), MyRemoteBooking.this);
            }
        });
    }

    public void getServices() {
        activityConstant.showProgressDialog(this, "wait...");
        final List<DialogItem> mList = new ArrayList<DialogItem>();
        ServiceManager ss = new ServiceManager();
        Call<List<RemoteBookingServicesModel>> call = ss.apiService.getAllRemoteBookingServices();
        call.enqueue(new Callback<List<RemoteBookingServicesModel>>() {
            @Override
            public void onResponse(Call<List<RemoteBookingServicesModel>> call, Response<List<RemoteBookingServicesModel>> response) {
                if (response.body().size() > 0 && response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        DialogItem dialogItem = new DialogItem();
                        dialogItem.setId("" + response.body().get(i).getServiceId());
                        dialogItem.setName(response.body().get(i).getEn_name());
                        mList.add(dialogItem);
                    }
                    DialogUtils.dialogList(MyRemoteBooking.this, getResources().getString(R.string.selectService), mList, MyRemoteBooking.this, ActivityConstant.SERVICE_CODE);
                } else {
                    DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.noServicesFound), MyRemoteBooking.this);
                }
                activityConstant.hideProgressDialog();
            }
            @Override
            public void onFailure(Call<List<RemoteBookingServicesModel>> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.webservice_error), MyRemoteBooking.this);
            }
        });
    }

    private void getBranchDetailsByID() {
        activityConstant.showProgressDialog(this, "wait...");
        ServiceManager ss = new ServiceManager();
        Call<RemoteBookingBranchByIDModel> call = ss.apiService.getAllBranchDetailsById(mSelectedBranchId);
        call.enqueue(new Callback<RemoteBookingBranchByIDModel>() {
            @Override
            public void onResponse(Call<RemoteBookingBranchByIDModel> call, Response<RemoteBookingBranchByIDModel> response) {
                Log.e("Service Length: ", response.body().getName() + "");
                if (response.isSuccessful() && response.body() != null) {
                    dialogRemoteBookingConfirmed(response.body());
                    mExtendedTime = response.body().getTotalWaitingTime();
                } else {
                    DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.err_internet), MyRemoteBooking.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<RemoteBookingBranchByIDModel> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.webservice_error), MyRemoteBooking.this);
            }
        });
    }

    public void issueTicketRemoteBooking() {
        activityConstant.showProgressDialog(this, "wait...");
        ServiceManager ss = new ServiceManager();
        Call<RemoteBookingIssueTicketModel> call = ss.apiService.remoteBookingIssueTicket(mSelectedServiceId, mSelectedBranchId, mSelectedDelay);
        call.enqueue(new Callback<RemoteBookingIssueTicketModel>() {
            @Override
            public void onResponse(Call<RemoteBookingIssueTicketModel> call, Response<RemoteBookingIssueTicketModel> response) {
                Log.e("Service Length: ", response.body().getTicketNumber() + "");
                if (response.isSuccessful() && response.body() != null) {
                    mButtonLayout.setVisibility(View.GONE);
                    mContactinfoLayout.setVisibility(View.GONE);
                    mWaitQueueLayout.setVisibility(View.GONE);
                    mAverageTimeLayout.setVisibility(View.GONE);
                    mTicketLayout.setVisibility(View.VISIBLE);
                    mCancelLayout.setVisibility(View.VISIBLE);
                    mTicketNumber.setText(response.body().getTicketNumber());
                    Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
                    calendar.add(Calendar.SECOND, Integer.valueOf(mExtendedTime));
                    System.out.println(calendar.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    System.out.println(sdf.format(calendar.getTime()));
                    mVisitid="" + response.body().getVisitId();
                    db.addData(mSelectedServiceId, mSelectedServiceName, mSelectedBranchId, mSelectedBranchName, response.body().getTicketNumber(), "" + sdf.format(calendar.getTime()), "0", "Booked", "" + response.body().getVisitId());
               // Clear all values
                    selectedBranchTextView.setText("");
                    selectedServiceTextView.setText("");
                    selectedAvilableDatedTextView.setText("");
//                    mSelectedServiceId ="";
//                    mSelectedServiceName="";
//                    mSelectedBranchId="";
//                    mSelectedBranchName="";
                } else {
                    DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.servicenotavailble), MyRemoteBooking.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<RemoteBookingIssueTicketModel> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.webservice_error), MyRemoteBooking.this);
            }
        });
    }


    @Override
    public void mDialogSelectedType(String mMesage, int i) {
    }

    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {
        if (mDialogItem != null && i == ActivityConstant.BRANCH_CODE) {
            selectedBranchTextView.setText(mDialogItem.getName());
            mSelectedBranchId = mDialogItem.getId();
            mSelectedBranchName = mDialogItem.getName();
        } else if (mDialogItem != null && i == ActivityConstant.SERVICE_CODE) {
            selectedServiceTextView.setText(mDialogItem.getName());
            mSelectedServiceId = mDialogItem.getId();
            mSelectedServiceName = mDialogItem.getName();
        } else if (mDialogItem != null && i == ActivityConstant.EXPECTEDTIME_CODE) {
            selectedAvilableDatedTextView.setText(mDialogItem.getName());
            mSelectedDelay = mDialogItem.getId();
            mSelectedDelayTime = mDialogItem.getName();
        }

    }

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {
    }


    public boolean isValidationSuccess() {
        if (selectedServiceTextView.getText().toString().length() == 0) {
            //   txtviewErrorServiceSelection.setVisibility(View.VISIBLE);
            DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.selectservice), MyRemoteBooking.this);
            Animation shake = AnimationUtils.loadAnimation(MyRemoteBooking.this, R.anim.shake);
            selectServiceDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorServiceSelection.setVisibility(View.GONE);

        if (selectedBranchTextView.getText().toString().length() == 0) {
            DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.selectBranchs), MyRemoteBooking.this);
            // txtviewErrorBranchSelection.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(MyRemoteBooking.this, R.anim.shake);
            selectBranchDropDown.startAnimation(shake);

            return false;
        } else
            txtviewErrorBranchSelection.setVisibility(View.GONE);


        if (selectedAvilableDatedTextView.getText().toString().length() == 0) {
            DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.selectexopectedtime), MyRemoteBooking.this);
            // txtviewErrorDateSelection.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(MyRemoteBooking.this, R.anim.shake);
            selectAvilableDatesDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorDateSelection.setVisibility(View.GONE);
        return true;
    }


    private void getCurrentLocation() {
        gps = new GPSTracker(MyRemoteBooking.this);
        if (gps.canGetLocation()) {
            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();
        } else {
            Toast.makeText(MyRemoteBooking.this, "Please enable your device location service.", Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogRemoteBookingConfirmed(RemoteBookingBranchByIDModel mRemoteBookingBranchByIDModel) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mRemoteBookingBranchByIDModel.getName();
        // int height = (int) (metrics.heightPixels * 0.7);
        final Dialog dialog = new Dialog(this, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remote_booking_confirm_qrcode);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);


        TextView mServiceNameView = (TextView) dialog.findViewById(R.id.RemoteServiceNameView);
        TextView mBranchNameView = (TextView) dialog.findViewById(R.id.RemoteBranchNameView);
        TextView mWaitingQueueView = (TextView) dialog.findViewById(R.id.RemoteWaitQueueNameView);
        TextView mServingTimeView = (TextView) dialog.findViewById(R.id.RemoteServingTimeView);
        TextView mExpectedTimeView = (TextView) dialog.findViewById(R.id.RemoteExpectedTimeView);

        mButtonLayout = (LinearLayout) dialog.findViewById(R.id.buttonLayout);
        mTicketLayout = (LinearLayout) dialog.findViewById(R.id.ticketLayout);
        mContactinfoLayout = (LinearLayout) dialog.findViewById(R.id.contactinfoLayout);
        mCancelLayout = (LinearLayout) dialog.findViewById(R.id.cancel_Layout);
        mWaitQueueLayout = (RelativeLayout) dialog.findViewById(R.id.waitingTimeLayout);
        mAverageTimeLayout = (RelativeLayout) dialog.findViewById(R.id.AverageServingtimeLayout);
        mTicketNumber = (TextView) dialog.findViewById(R.id.TicketNumber);

        mServiceNameView.setText(mSelectedServiceName);
        mBranchNameView.setText(mSelectedBranchName);
        mWaitingQueueView.setText(mRemoteBookingBranchByIDModel.getCustomersWaiting());
        mServingTimeView.setText(mRemoteBookingBranchByIDModel.getAverageWaitingTime());
        mExpectedTimeView.setText(mRemoteBookingBranchByIDModel.getTotalWaitingTime());

        Button mBookButton = (Button) dialog.findViewById(R.id.mBookingButton);
        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueTicketRemoteBooking();
            }
        });
        Button mCancelButton = (Button) dialog.findViewById(R.id.CancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTicket(mSelectedServiceId,mSelectedBranchId,mVisitid, dialog);
            }
        });

        ImageView imageViewClose = (ImageView) dialog.findViewById(R.id.imageViewClose);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void deleteTicket(String mServiceID, String mBranchID, String mVisitID,final Dialog mDialog){
        activityConstant.showProgressDialog(this, "wait...");
        ServiceManager ss = new ServiceManager();
        Call<DeleteTicket> call = ss.apiService.deleteTicket(mServiceID, mBranchID, mVisitID);
        call.enqueue(new Callback<DeleteTicket>() {
            @Override
            public void onResponse(Call<DeleteTicket> call, Response<DeleteTicket> response) {
                Log.e("Service Length: ", response.body()+"");
                if (response.isSuccessful() && response.body() != null) {

                    if(response.body().getIsdeleted().equals("true")) {
                        db.deleteRemoteBooking(mTicketNumber.getText().toString());
                        DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.deletsuccessfully), MyRemoteBooking.this);
                    }else{
                        DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.servicenotavailble), MyRemoteBooking.this);
                    }
                    mDialog.dismiss();
                } else {
                    DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.err_internet), MyRemoteBooking.this);
                }
                activityConstant.hideProgressDialog();
            }
            @Override
            public void onFailure(Call<DeleteTicket> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyRemoteBooking.this, getResources().getString(R.string.webservice_error), MyRemoteBooking.this);
            }
        });
    }
}
