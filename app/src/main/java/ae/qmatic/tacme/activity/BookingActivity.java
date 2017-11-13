package ae.qmatic.tacme.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.db.DatabaseHandler;
import ae.qmatic.tacme.model.AppointmentResponseModel;
import ae.qmatic.tacme.model.ConfirmAppointmentResponseModel;
import ae.qmatic.tacme.model.ConfirmedAppointmentRequestModel;
import ae.qmatic.tacme.model.DeleteAppointment;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.GetAllBranches;
import ae.qmatic.tacme.model.GetAvailableDates;
import ae.qmatic.tacme.model.GetAvailableTimes;
import ae.qmatic.tacme.model.ServiceListModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.GPSTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev3 on 8/10/16.
 */
public class BookingActivity extends AppCompatActivity implements DialogUtils.DialogListner {
    private TextView mSelectedBranchTextView, mSelectedServiceTextView, mSelectedDateTextView, mSelectedTimeTextView;
    private TextView txtviewErrorAppTimeSelection, txtviewErrorAppDateSelection, txtviewErrorServiceSelection, txtviewErrorBranchSelection;
    private LinearLayout mSelectBranchDropDown, mSelectServiceDropDown, mSelectDateDropDown, mSelectTimeDropDown;
    private ImageView selectAppointmentDateDropDown;
    private String mBranchNameStr, mServiceNameStr;
    private String mServiceId = "", mBranchId = "", mAvailableDateStr = "", mAvailableTimeStr = "";
    ActivityConstant activityConstant;
    private Button buttonNext;
    GPSTracker gps;
    double latitude, longitude;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity_layout);
        activityConstant = new ActivityConstant();
        db = new DatabaseHandler(this);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.bookAppointment));
        ImageView imgRightIcon = (ImageView) toolbarTop.findViewById(R.id.imgRightIcon);
        imgRightIcon.setVisibility(View.GONE);
        getCurrentLocation();
        initialseUI();
        setListners();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initialseUI() {
        //Next Green colour button
        buttonNext = (Button) findViewById(R.id.buttonNext);
        //// Textview fields value

        mSelectedBranchTextView = (TextView) findViewById(R.id.selectedBranchTextView);
        mSelectedServiceTextView = (TextView) findViewById(R.id.selectedServiceTextView);
        mSelectedDateTextView = (TextView) findViewById(R.id.selectedAppointmentDateTextView);
        mSelectedTimeTextView = (TextView) findViewById(R.id.selectedAppointmentTimeTextView);

        ///////Drop down Icon

        mSelectBranchDropDown = (LinearLayout) findViewById(R.id.selectBranchDropDown);
        mSelectServiceDropDown = (LinearLayout) findViewById(R.id.selectServiceDropDown);
        mSelectDateDropDown = (LinearLayout) findViewById(R.id.selectDateDropDown);
        mSelectTimeDropDown = (LinearLayout) findViewById(R.id.selectTimeDropDown);
        selectAppointmentDateDropDown = (ImageView) findViewById(R.id.selectAppointmentDateDropDown);


        /// error messages
        txtviewErrorAppTimeSelection = (TextView) findViewById(R.id.txtviewErrorAppTimeSelection);
        txtviewErrorAppDateSelection = (TextView) findViewById(R.id.txtviewErrorAppDateSelection);
        txtviewErrorServiceSelection = (TextView) findViewById(R.id.txtviewErrorServiceSelection);
        txtviewErrorBranchSelection = (TextView) findViewById(R.id.txtviewErrorBranchSelection);


    }

    private void setListners() {

        mSelectServiceDropDown.setOnClickListener(onClickListner);
        mSelectDateDropDown.setOnClickListener(onClickListner);
        mSelectBranchDropDown.setOnClickListener(onClickListner);
        buttonNext.setOnClickListener(onClickListner);
        mSelectTimeDropDown.setOnClickListener(onClickListner);

        //createAppointmentButtons();
    }

    private void getCurrentLocation() {
        // create class object
        gps = new GPSTracker(BookingActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            // gps.showSettingsAlert();
        }
    }

    View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonNext:
                    if (isValidationSuccess()) {
                        addAppointment(mBranchId, mServiceId, mAvailableDateStr, mAvailableTimeStr);
                    }
                    break;

                case R.id.selectBranchDropDown:

                    if (mSelectedServiceTextView.getText().toString().trim().length() > 0) {
                        getBranchesbasedonService(mServiceId);
                    } else {
                        DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectService), BookingActivity.this);
                    }

                    break;

                case R.id.selectServiceDropDown:
                    getservices();

                    break;
                case R.id.selectDateDropDown:
                    if ((mSelectedServiceTextView.getText().toString().trim().length() > 0) && (mSelectedBranchTextView.getText().toString().trim().length() > 0)) {
                        getAvailableDates(mBranchId, mServiceId);
                    } else {
                        DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectBranchs), BookingActivity.this);
                    }

                    break;
                case R.id.selectTimeDropDown:
                    if ((mSelectedServiceTextView.getText().toString().trim().length() > 0) && (mSelectedBranchTextView.getText().toString().trim().length() > 0) && (mSelectedDateTextView.getText().toString().trim().length() > 0)) {
                        getAvailableTimes(mBranchId, mServiceId, mAvailableDateStr);
                    } else {
                        DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectavailabledate), BookingActivity.this);
                    }

                    break;
            }


        }
    };

    @Override
    public void mDialogSelectedType(String mMessage, int i) {

        if (i == 10001) {

            deleteApointment(mMessage, true);

        } else if (i == 10002) {
            deleteApointment(mMessage, false);
        }else if(i==10003){
            finish();
        }


    }

    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {
        if (mDialogItem != null && i == ActivityConstant.SERVICE_CODE) {
            mSelectedServiceTextView.setText(mDialogItem.getName());
            mServiceNameStr = mDialogItem.getName();
            mServiceId = mDialogItem.getId();

        } else if (mDialogItem != null && i == ActivityConstant.BRANCH_CODE) {
            mSelectedBranchTextView.setText(mDialogItem.getName());
            mBranchNameStr = mDialogItem.getName();
            mBranchId = mDialogItem.getId();
        } else if (mDialogItem != null && i == ActivityConstant.DATE_PICKER_CODE) {
            mSelectedDateTextView.setText(mDialogItem.getName());
            mAvailableDateStr = mDialogItem.getName();
        } else if (mDialogItem != null && i == ActivityConstant.TIME_PICKER_CODE) {
            mSelectedTimeTextView.setText(mDialogItem.getName());
            mAvailableTimeStr = mDialogItem.getName();
        }

    }

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {

        bookingTicket(mHashMap);
    }

    public boolean isValidationSuccess() {

        //////Service validation
        if (mSelectedServiceTextView.getText().toString().length() == 0) {
            // txtviewErrorServiceSelection.setVisibility(View.VISIBLE);
            DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectservice), BookingActivity.this);
            Animation shake = AnimationUtils.loadAnimation(BookingActivity.this, R.anim.shake);
            mSelectServiceDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorServiceSelection.setVisibility(View.GONE);
        //////Branch validation
        if (mSelectedBranchTextView.getText().toString().length() == 0) {
            //  txtviewErrorBranchSelection.setVisibility(View.VISIBLE);
            DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectBranchs), BookingActivity.this);
            Animation shake = AnimationUtils.loadAnimation(BookingActivity.this, R.anim.shake);
            mSelectBranchDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorBranchSelection.setVisibility(View.GONE);


        //////appointment date validation
        if (mSelectedDateTextView.getText().toString().length() == 0) {
            //  txtviewErrorAppDateSelection.setVisibility(View.VISIBLE);
            DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectavailabledate), BookingActivity.this);
            Animation shake = AnimationUtils.loadAnimation(BookingActivity.this, R.anim.shake);
            mSelectDateDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorAppDateSelection.setVisibility(View.GONE);

        //////appointment time validation
        if (mSelectedTimeTextView.getText().toString().length() == 0) {
            //txtviewErrorAppTimeSelection.setVisibility(View.VISIBLE);
            DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.selectavailabletime), BookingActivity.this);
            Animation shake = AnimationUtils.loadAnimation(BookingActivity.this, R.anim.shake);
            mSelectTimeDropDown.startAnimation(shake);
            return false;
        } else
            txtviewErrorAppTimeSelection.setVisibility(View.GONE);

        return true;
    }


    public void getBranchesbasedonService(String serviceId) {
        activityConstant.showProgressDialog(this, "Loading...");
        ServiceManager ss = new ServiceManager();
        Call<List<GetAllBranches>> call = ss.apiService.getAllBranchesBasedonService(serviceId);
        call.enqueue(new Callback<List<GetAllBranches>>() {
            @Override
            public void onResponse(Call<List<GetAllBranches>> call, Response<List<GetAllBranches>> response) {
                if (response.body().size() > 0 && response.isSuccessful() && response.body() != null) {
                    List<DialogItem> branchList = new ArrayList<DialogItem>();
                    for (int i = 0; i < response.body().size(); i++) {
                        DialogItem dialogItem = new DialogItem();
                        dialogItem.setName(response.body().get(i).getName());
                        dialogItem.setId(response.body().get(i).getId());
                        branchList.add(dialogItem);
                    }
                    DialogUtils.dialogList(BookingActivity.this, getResources().getString(R.string.selectBranch), branchList, BookingActivity.this, ActivityConstant.BRANCH_CODE);

                    mSelectedDateTextView.setText("");
                    mSelectedTimeTextView.setText("");
                } else {
                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.noBranchesFound), BookingActivity.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<GetAllBranches>> call, Throwable t) {
                activityConstant.hideProgressDialog();

                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }

    private void getservices() {
        activityConstant.showProgressDialog(this, "Loading...");
        ServiceManager ss = new ServiceManager();
        Call<List<ServiceListModel>> call = ss.apiService.getAllServices();
        call.enqueue(new Callback<List<ServiceListModel>>() {
            @Override
            public void onResponse(Call<List<ServiceListModel>> call, Response<List<ServiceListModel>> response) {
                if (response.body().size() > 0 && response.isSuccessful() && response.body() != null) {
                    List<DialogItem> serviceList = new ArrayList<DialogItem>();
                    for (int i = 0; i < response.body().size(); i++) {
                        DialogItem dialogItem = new DialogItem();
                        dialogItem.setName(response.body().get(i).getName());
                        dialogItem.setId(response.body().get(i).getId());
                        serviceList.add(dialogItem);
                    }
                    DialogUtils.dialogList(BookingActivity.this, getResources().getString(R.string.selectService), serviceList, BookingActivity.this, ActivityConstant.SERVICE_CODE);

                    mSelectedBranchTextView.setText("");
                    mSelectedDateTextView.setText("");
                    mSelectedTimeTextView.setText("");
                } else {
                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.noServicesFound), BookingActivity.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<ServiceListModel>> call, Throwable t) {
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }


    public void getAvailableDates(String mBranchIdstr, String mServiceIdstr) {
        activityConstant.showProgressDialog(this, "Loading...");
        final List<DialogItem> mList = new ArrayList<DialogItem>();
        ServiceManager ss = new ServiceManager();
        Call<GetAvailableDates> call = ss.apiService.getAvilableDates(mBranchIdstr, mServiceIdstr);
        call.enqueue(new Callback<GetAvailableDates>() {
            @Override
            public void onResponse(Call<GetAvailableDates> call, Response<GetAvailableDates> response) {
                if (response.body().getAvailableDate().size() > 0 && response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().getAvailableDate().size(); i++) {
                        Log.e("Available dates  ===>  ", "" + response.body().getAvailableDate().get(i).getDate());
                        DialogItem dialogItem = new DialogItem();
                        //dialogItem.setId("" + response.body().get(i).getId());
                        dialogItem.setName(response.body().getAvailableDate().get(i).getDate());
                        mList.add(dialogItem);
                    }
                    DialogUtils.dialogList(BookingActivity.this, getResources().getString(R.string.selectBranch), mList, BookingActivity.this, ActivityConstant.DATE_PICKER_CODE);
                } else {

                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.nodatesFound), BookingActivity.this);
                }
                activityConstant.hideProgressDialog();

                mSelectedTimeTextView.setText("");
            }

            @Override
            public void onFailure(Call<GetAvailableDates> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }

    public void getAvailableTimes(String mBranchIdstr, String mServiceIdstr, String mAvailableDate) {
        activityConstant.showProgressDialog(this, "Loading...");
        Log.e("Avilable Times", "Called");
        final List<DialogItem> mList = new ArrayList<DialogItem>();
        ServiceManager ss = new ServiceManager();
        Call<GetAvailableTimes> call = ss.apiService.getAvilableTimes(mBranchIdstr, mServiceIdstr, mAvailableDate);
        call.enqueue(new Callback<GetAvailableTimes>() {
            @Override
            public void onResponse(Call<GetAvailableTimes> call, Response<GetAvailableTimes> response) {
                if (response.body().getAvailableTime().size() > 0 && response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().getAvailableTime().size(); i++) {
                        Log.e("Available dates  ===>  ", "" + response.body().getAvailableTime().get(i).getTime());
                        DialogItem dialogItem = new DialogItem();
                        //dialogItem.setId("" + response.body().get(i).getId());
                        dialogItem.setName(response.body().getAvailableTime().get(i).getTime());
                        mList.add(dialogItem);
                    }
                    DialogUtils.dialogList(BookingActivity.this, getResources().getString(R.string.selectBranch), mList, BookingActivity.this, ActivityConstant.TIME_PICKER_CODE);
                } else {
                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.notimesfound), BookingActivity.this);
                }
                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<GetAvailableTimes> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }

    public void addAppointment(String mBranchIdstr, String mServiceIdstr, String mAvailableDate, String mAvailableTime) {
        activityConstant.showProgressDialog(this, "Loading...");


        ServiceManager sServiceManager = new ServiceManager();
        Call<AppointmentResponseModel> call = sServiceManager.apiService.addAppointment(mBranchIdstr, mServiceIdstr, mAvailableDate, mAvailableTime);
        call.enqueue(new Callback<AppointmentResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentResponseModel> call, Response<AppointmentResponseModel> response) {
                if (response.code() == 200) {

                    HashMap mHashMap = new HashMap();
                    mHashMap.put("DATE", response.body().getAppointment().getDate());
                    mHashMap.put("TIME", response.body().getAppointment().getTime());
                    mHashMap.put("BRANCH", response.body().getAppointment().getBranchName());
                    mHashMap.put("CUSTMERID", response.body().getAppointment().getId());
                    mHashMap.put("SERVICE", response.body().getAppointment().getServiceName());
                    DialogUtils.dialogConfirmInfoPopup(BookingActivity.this, mHashMap, BookingActivity.this);

                } else {
                    Log.e("ServiceId ====", "" + mServiceId);
                    Log.e("Branch id ====", "" + mBranchId);
                    Log.e("Date Str ====", "" + mAvailableDateStr);
                    Log.e("Time Str  ====", "" + mAvailableTimeStr);
                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
                }


                activityConstant.hideProgressDialog();

            }

            @Override
            public void onFailure(Call<AppointmentResponseModel> call, Throwable t) {
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }

    private void bookingTicket(final HashMap mtmHashMap) {
        activityConstant.showProgressDialog(this, "Loading...");
        /// to get User data
        SharedPreferences shrPref = PreferenceManager.getDefaultSharedPreferences(BookingActivity.this);
        String userId = shrPref.getString("userId", "user");
        String userEmail = shrPref.getString("emailId", "email");
        ConfirmedAppointmentRequestModel mConfirmedAppointmentRequestModel = new ConfirmedAppointmentRequestModel();
        ConfirmedAppointmentRequestModel.AppointmentEntity mAppointmentEntity = new ConfirmedAppointmentRequestModel.AppointmentEntity();
        ConfirmedAppointmentRequestModel.AppointmentEntity.CustomerEntity mCustomerEntity = new ConfirmedAppointmentRequestModel.AppointmentEntity.CustomerEntity();
        mCustomerEntity.setPhone("099988888");
        mCustomerEntity.setEmail(userEmail);
        mAppointmentEntity.setCustomer(mCustomerEntity);
        mConfirmedAppointmentRequestModel.setAppointment(mAppointmentEntity);

        ServiceManager sServiceManager = new ServiceManager();
        Call<ConfirmAppointmentResponseModel> call = sServiceManager.apiService.confirmAppointment(mtmHashMap.get("CUSTMERID").toString(), mConfirmedAppointmentRequestModel);
        call.enqueue(new Callback<ConfirmAppointmentResponseModel>() {
            @Override
            public void onResponse(Call<ConfirmAppointmentResponseModel> call, Response<ConfirmAppointmentResponseModel> response) {
                if (response.code() == 200) {
                    mtmHashMap.put("IDS", "" + response.body().getAppointment().getId());
                    DialogUtils.dialogAppointmentConfirmed(BookingActivity.this, mtmHashMap, BookingActivity.this);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    //get current date time with Date()
                    Date date =null;
                    try {
                        date = dateFormat.parse(mtmHashMap.get("DATE").toString() + " " + mtmHashMap.get("TIME"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmm");
                    dateFormat2.format(date);

                    System.out.println(dateFormat2.format(date));

                    //get current date time with Calendar()

                    Log.e("fffffffffff",""+ dateFormat2.format(date));

                    db.addAppointment(mServiceId, mServiceNameStr, mBranchId, mBranchNameStr, response.body().getAppointment().getCustomer().getEmail(),
                            response.body().getAppointment().getCustomer().getFirstName(),
                            response.body().getAppointment().getCustomer().getLastName(),
                            "" + response.body().getAppointment().getCustomer().getPhone(),
                            response.body().getAppointment().getDate(),
                            response.body().getAppointment().getExternalId(),
                            response.body().getAppointment().getTime(),
                            response.body().getAppointment().getId(),
                            "0",dateFormat2.format(date)

                    );

                }
                activityConstant.hideProgressDialog();
                mSelectedServiceTextView.setText("");
                mSelectedBranchTextView.setText("");

                mSelectedDateTextView.setText("");
                mSelectedTimeTextView.setText("");
            }

            @Override
            public void onFailure(Call<ConfirmAppointmentResponseModel> call, Throwable t) {
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }

        });
    }

    private void deleteApointment(final String mAppointmentId, final boolean isPopupEnabled) {
        activityConstant.showProgressDialog(this, "Loading...");
        ServiceManager ss = new ServiceManager();
        Call<DeleteAppointment> call = ss.apiService.deleteapointment(mAppointmentId);
        call.enqueue(new Callback<DeleteAppointment>() {
            @Override
            public void onResponse(Call<DeleteAppointment> call, Response<DeleteAppointment> response) {

                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getIsDeleted().equals("true")) {
                        if (isPopupEnabled) {
                            DialogUtils.singleButtonDialogCancel(BookingActivity.this, response.body().getMessage(), BookingActivity.this);
                        }
                        db.updateAppointmentStatus(mAppointmentId, "1");


                    } else {
                        DialogUtils.singleButtonDialog(BookingActivity.this, response.body().getMessage(), BookingActivity.this);
                    }
                } else {
                    DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.servicenotavailble), BookingActivity.this);
                }

                activityConstant.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<DeleteAppointment> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(BookingActivity.this, getResources().getString(R.string.webservice_error), BookingActivity.this);
            }
        });
    }


}
