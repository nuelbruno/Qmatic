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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.adapter.AppointmentListAdapter;
import ae.qmatic.tacme.db.DatabaseHandler;
import ae.qmatic.tacme.model.AppointmentBookingListModel;
import ae.qmatic.tacme.model.DeleteAppointment;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by mdev3 on 8/31/16.
 */
public class MyAppointmentListActivity extends AppCompatActivity implements DialogUtils.DialogListner {
    DatabaseHandler db;
    ActivityConstant activityConstant;
    private ListView mBookingListView;
    List<AppointmentBookingListModel> mList;
    AppointmentListAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointmentlist_layout);
        activityConstant = new ActivityConstant();
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.myApponitment));
        db = new DatabaseHandler(this);
        Log.e("Count ", ""+db.getListAppointmentBooking().size());
        mList = db.getListAppointmentBooking();
        mBookingListView = (ListView)findViewById(R.id.BooloingListView);
        adapter = new AppointmentListAdapter(this,mList );
        mBookingListView.setAdapter(adapter);
        mBookingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogAppointmentConfirmed(mList.get(position));
            }
        });
    }

    private void dialogAppointmentConfirmed(final AppointmentBookingListModel mAppointmentBookingListModel) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(this, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_confirmed_qrcode);
        dialog.setCancelable(true);
        //dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView mDateView = (TextView) dialog.findViewById(R.id.AppointmentDateView);
        mDateView.setText(mAppointmentBookingListModel.getDate());
        TextView mTimeView = (TextView) dialog.findViewById(R.id.AppointTimeView);
        mTimeView.setText(mAppointmentBookingListModel.getTime());

        TextView mBranchNameView = (TextView) dialog.findViewById(R.id.BranchNameView);
        mBranchNameView.setText(mAppointmentBookingListModel.getBranchName());
        TextView mServiceNameView = (TextView) dialog.findViewById(R.id.ServiceNameView);
        mServiceNameView.setText(mAppointmentBookingListModel.getServiceName());
        TextView mStatusNameView = (TextView) dialog.findViewById(R.id.StatusView);
        LinearLayout mButtonLayout = (LinearLayout) dialog.findViewById(R.id.ButtonLayout);
        if(mAppointmentBookingListModel.getStatus().equals("0")) {
            mStatusNameView.setText("Booked");
        }
        else if(mAppointmentBookingListModel.getStatus().equals("1")){
            mStatusNameView.setText("Cancelled");
            mButtonLayout.setVisibility(View.GONE);
        }
        Button btnRechedule = (Button) dialog.findViewById(R.id.btnRechedule);
        btnRechedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteApointment(mAppointmentBookingListModel.getIds());
            }
        });
        dialog.show();

    }

    private void deleteApointment(final String mAppointmentId){
        activityConstant.showProgressDialog(this, "wait...");
        ServiceManager ss = new ServiceManager();
        Call<DeleteAppointment> call = ss.apiService.deleteapointment(mAppointmentId);
        call.enqueue(new Callback<DeleteAppointment>() {
            @Override
            public void onResponse(Call<DeleteAppointment> call, Response<DeleteAppointment> response) {

                if (response.isSuccessful() && response.body() != null) {

                    if(response.body().getIsDeleted().equals("true")) {

                        DialogUtils.singleButtonDialog(MyAppointmentListActivity.this,  response.body().getMessage(), MyAppointmentListActivity.this);
                        db.updateAppointmentStatus(mAppointmentId, "1");
                        mList = db.getListAppointmentBooking();
                        adapter = new AppointmentListAdapter(MyAppointmentListActivity.this, mList);

                        mBookingListView.setAdapter(adapter);


                    }else{
                        DialogUtils.singleButtonDialog(MyAppointmentListActivity.this,  response.body().getMessage(), MyAppointmentListActivity.this);
                    }
                } else {
                    DialogUtils.singleButtonDialog(MyAppointmentListActivity.this, getResources().getString(R.string.servicenotavailble), MyAppointmentListActivity.this);
                }

                activityConstant.hideProgressDialog();
            }
            @Override
            public void onFailure(Call<DeleteAppointment> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyAppointmentListActivity.this, getResources().getString(R.string.err_internet), MyAppointmentListActivity.this);
            }
        });
    }

    @Override
    public void mDialogSelectedType(String mMesage, int i) {

    }

    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {

    }

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {

    }
}
