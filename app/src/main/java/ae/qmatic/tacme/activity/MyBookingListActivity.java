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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.adapter.ListViewCustomAdapter;
import ae.qmatic.tacme.db.DatabaseHandler;
import ae.qmatic.tacme.model.DeleteTicket;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.RemoteBookingListModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.DialogUtils.DialogListner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev3 on 8/10/16.
 */
public class MyBookingListActivity extends AppCompatActivity implements DialogListner
{
    ListViewCustomAdapter adapter;
    ActivityConstant activityConstant;
    private ListView mBookingListView;
    DatabaseHandler db;
    List<RemoteBookingListModel> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybooking_activity_layout);
        db = new DatabaseHandler(this);
        activityConstant = new ActivityConstant();
        mList = db.getListRemoteBooking();
        for(int i = 0; i<mList.size();i++){
        Log.e("Index"+i, mList.get(i).getService_name()+"  "+mList.get(i).getTime());        }
        mBookingListView = (ListView)findViewById(R.id.BooloingListView);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.myRemoteBooking));

        ImageView imgRightIcon = (ImageView) toolbarTop.findViewById(R.id.imgRightIcon);
        imgRightIcon.setVisibility(View.GONE);
        adapter = new ListViewCustomAdapter(this, mList);
        mBookingListView.setAdapter(adapter);
        mBookingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogRemoteBookingConfirmed(mList.get(position));
            }
        });
    }
    public void dialogRemoteBookingConfirmed(final RemoteBookingListModel mRemoteBookingListModel) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mRemoteBookingListModel.getService_name();
        // int height = (int) (metrics.heightPixels * 0.7);
        final Dialog dialog = new Dialog(this, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remote_booking_confirm_qrcode);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        TextView mServiceNameView    = (TextView)dialog.findViewById(R.id.RemoteServiceNameView);
        TextView mBranchNameView     = (TextView)dialog.findViewById(R.id.RemoteBranchNameView);
        TextView mWaitingQueueView   = (TextView)dialog.findViewById(R.id.RemoteWaitQueueNameView);
        TextView mServingTimeView    = (TextView)dialog.findViewById(R.id.RemoteServingTimeView);
        TextView mExpectedTimeView   = (TextView)dialog.findViewById(R.id.RemoteExpectedTimeView);

        LinearLayout  mButtonLayout = (LinearLayout)dialog.findViewById(R.id.buttonLayout);
        mButtonLayout.setVisibility(View.VISIBLE);
        LinearLayout mTicketLayout = (LinearLayout)dialog.findViewById(R.id.ticketLayout);
        mTicketLayout.setVisibility(View.GONE);
        LinearLayout  mContactinfoLayout = (LinearLayout)dialog.findViewById(R.id.contactinfoLayout);
        mContactinfoLayout.setVisibility(View.GONE);
        RelativeLayout mWaitQueueLayout = (RelativeLayout) dialog.findViewById(R.id.waitingTimeLayout);
        mWaitQueueLayout.setVisibility(View.GONE);
        RelativeLayout mAverageTimeLayout = (RelativeLayout) dialog.findViewById(R.id.AverageServingtimeLayout);
        mAverageTimeLayout.setVisibility(View.GONE);
        TextView  mTicketNumber = (TextView)dialog.findViewById(R.id.TicketNumber);

        mServiceNameView.setText( mRemoteBookingListModel.getService_name());
        mBranchNameView.setText( mRemoteBookingListModel.getBranch_name());
        mWaitingQueueView.setText(mRemoteBookingListModel.getWaitingQueue());
        mServingTimeView.setText(mRemoteBookingListModel.getAverageServingTime());
        mExpectedTimeView.setText(mRemoteBookingListModel.getTime());
        Button mpostpondButton = (Button)dialog.findViewById(R.id.mPrevoiusButton);
        mpostpondButton.setText("Postponed");
        Button mCancelButton = (Button)dialog.findViewById(R.id.mBookingButton);
        mCancelButton.setText("Cancel");

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTicket(mRemoteBookingListModel.getService_id(),mRemoteBookingListModel.getBranch_id(),mRemoteBookingListModel.getVisitId(), mRemoteBookingListModel.getTicket(),dialog);
            }
        });

        ImageView imageViewClose = (ImageView)dialog.findViewById(R.id.imageViewClose);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private void deleteTicket(String mServiceID, String mBranchID, String mVisitID, final String ticket, final Dialog mDialog){
        activityConstant.showProgressDialog(this, "wait...");
        ServiceManager ss = new ServiceManager();
        Call<DeleteTicket> call = ss.apiService.deleteTicket(mServiceID, mBranchID, mVisitID);
        call.enqueue(new Callback<DeleteTicket>() {
            @Override
            public void onResponse(Call<DeleteTicket> call, Response<DeleteTicket> response) {

                if (response.isSuccessful() && response.body() != null) {

                    if(response.body().getIsdeleted().equals("true")) {
                        db.deleteRemoteBooking(ticket);
                        DialogUtils.singleButtonDialog(MyBookingListActivity.this, getResources().getString(R.string.deletsuccessfully), MyBookingListActivity.this);
                        mList = db.getListRemoteBooking();
                        adapter = new ListViewCustomAdapter(MyBookingListActivity.this, mList);

                        mBookingListView.setAdapter(adapter);


                    }
                } else {
                    DialogUtils.singleButtonDialog(MyBookingListActivity.this, getResources().getString(R.string.servicenotavailble), MyBookingListActivity.this);
                }
                mDialog.dismiss();
                activityConstant.hideProgressDialog();
            }
            @Override
            public void onFailure(Call<DeleteTicket> call, Throwable t) {
                Log.e("Error", "" + t.toString());
                activityConstant.hideProgressDialog();
                DialogUtils.singleButtonDialog(MyBookingListActivity.this, getResources().getString(R.string.err_internet), MyBookingListActivity.this);
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
