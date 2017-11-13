package ae.qmatic.tacme.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ae.qmatic.tacme.R;


public class MainActivity extends AppCompatActivity {

    private LinearLayout mLocateBranchButton;
    private LinearLayout mRemoteBookingButton;
    private LinearLayout mBookAppointmentButton;
    private LinearLayout mMyRemoteBookingButton;
    private LinearLayout mMyAppointmentButton;

    private TextView mTimerHourFirstDigit;
    private TextView mTimerHourSecondDigit;
    private TextView mTimerMinFirstDigit;
    private TextView mTimerMinSecondDigit;
    private TextView mTimerSecondFirstDigit;
    private TextView mTimerSecondSecondDigit;
    LinearLayout mMostRecentBookingIncludeLayout;
    LinearLayout mMostRecentAppointmentIncludeLayout;

    long diff;
    long milliseconds;
    long endTime;

    MyCount counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        /*TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.bookAppointment));*/
        ImageView imgRightIcon = (ImageView)toolbarTop.findViewById(R.id.imgRightIcon);
        imgRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ApplicationSettings.class));
            }
        });


        initaializeUI();
        setListners();
        String oldTime = "30.08.2016, 02:00";
        //countDownTimer(oldTime);

        chekForLocationPermissionRuntime();
    }

    private void initaializeUI() {
        mLocateBranchButton = (LinearLayout) findViewById(R.id.LocationBranchButton);
        mRemoteBookingButton = (LinearLayout) findViewById(R.id.RemoteBookingButton);
        mBookAppointmentButton = (LinearLayout) findViewById(R.id.BookAppointmentButton);
        mMyRemoteBookingButton = (LinearLayout) findViewById(R.id.MyRemoteBookings);
        mMyAppointmentButton = (LinearLayout) findViewById(R.id.MyAppointmentButton);

        mMostRecentBookingIncludeLayout = (LinearLayout) findViewById(R.id.layoutMostRecentBooking);
        mMostRecentAppointmentIncludeLayout = (LinearLayout) findViewById(R.id.layoutMostRecentAppointment);

        mTimerHourFirstDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeHourFirstDigit);
        mTimerHourSecondDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeHourSecondDigit);
        mTimerMinFirstDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeMinFirstDigit);
        mTimerMinSecondDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeMinSecondDigit);
        mTimerSecondFirstDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeSecondFirstDigit);
        mTimerSecondSecondDigit = (TextView) mMostRecentBookingIncludeLayout.findViewById(R.id.timeSecondSecondDigit);
    }

    private void setListners() {
        mLocateBranchButton.setOnClickListener(onClickListners);
        mRemoteBookingButton.setOnClickListener(onClickListners);
        mBookAppointmentButton.setOnClickListener(onClickListners);
        mMyRemoteBookingButton.setOnClickListener(onClickListners);
        mMyAppointmentButton.setOnClickListener(onClickListners);
    }

    private void countDownTimer(String oldTime) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        Date oldDate;
        try {
            oldDate = formatter.parse(oldTime);
            milliseconds = oldDate.getTime();

            //long startTime = System.currentTimeMillis();
            // do your work...
            long endTime=System.currentTimeMillis();

            diff = endTime-milliseconds;

          //  Log.e("day", "miliday"+diff);
            long seconds = (long) (diff / 1000) % 60 ;
           // Log.e("secnd", "miliday"+seconds);
            long minutes = (long) ((diff / (1000*60)) % 60);
           // Log.e("minute", "miliday"+minutes);
            long hours   = (long) ((diff / (1000*60*60)) % 24);
          //  Log.e("hour", "miliday"+hours);
            long days = (int)((diff / (1000*60*60*24)) % 365);
           // Log.e("days", "miliday"+days);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Long serverUptimeSeconds = (System.currentTimeMillis() - milliseconds) / 1000;
        String serverUptimeText = String.format("%d days %d hours %d minutes %d seconds",
                serverUptimeSeconds / 86400,
                ( serverUptimeSeconds % 86400) / 3600 ,
                ((serverUptimeSeconds % 86400) % 3600 ) / 60,
                ((serverUptimeSeconds % 86400) % 3600 ) % 60
        );
        counter = new MyCount(milliseconds,1000);
        counter.start();
    }





    View.OnClickListener onClickListners = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.LocationBranchButton: {
                    Intent mIntent = new Intent(MainActivity.this,MapActivity.class);
                    startActivityForResult(mIntent, 1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                }
                break;
                case R.id.RemoteBookingButton: {
                    Intent mIntent = new Intent(MainActivity.this,MyRemoteBooking.class);
                    //mIntent.putExtra("BOOKING_TYPE",0);
                    startActivityForResult(mIntent, 1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
                break;
                case R.id.BookAppointmentButton: {
                    Intent mIntent = new Intent(MainActivity.this,BookingActivity.class);
                    //mIntent.putExtra("BOOKING_TYPE",1);
                    startActivityForResult(mIntent, 1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
                break;
                case R.id.MyRemoteBookings: {
                    Intent mIntent = new Intent(MainActivity.this,MyBookingListActivity.class);
                    mIntent.putExtra("BOOKING_List_TYPE",0);
                    startActivityForResult(mIntent, 1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
                break;
                case R.id.MyAppointmentButton: {
                    Intent mIntent = new Intent(MainActivity.this,MyAppointmentListActivity.class);
                    mIntent.putExtra("BOOKING_List_TYPE",1);
                    startActivityForResult(mIntent, 1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
                break;
            }

        }
    };


    // countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mTimerHourFirstDigit.setText("0");
            mTimerHourSecondDigit.setText("0");
            mTimerMinFirstDigit.setText("0" );
            mTimerMinSecondDigit.setText("0" );
            mTimerSecondFirstDigit.setText("0");
            mTimerSecondSecondDigit.setText("0");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //tv.setText("Left: " + millisUntilFinished / 1000);

            long diff = endTime - millisUntilFinished;
           // Log.e("left", "miliday"+diff);
            //long seconds = (long) (diff / 1000) % 60 ;
           // Log.e("secnd", "miliday"+seconds);
           // long minutes = (long) ((diff / (1000*60)) % 60);
           // Log.e("minute", "miliday"+minutes);
           // long hours   = (long) ((diff / (1000*60*60)) % 24);
          // Log.e("hour", "miliday"+hours);
           // int days = (int)((diff / (1000*60*60*24)) % 365);
            //Log.v("days", "miliday"+days);
            Long serverUptimeSeconds =
                    (millisUntilFinished-System.currentTimeMillis()  ) / 1000;

            mTimerHourFirstDigit.setText("" + String.format("%02d", ( serverUptimeSeconds % 86400) / 3600).charAt(0));
            mTimerHourSecondDigit.setText("" + String.format("%02d", ( serverUptimeSeconds % 86400) / 3600).charAt(1));
            mTimerMinFirstDigit.setText("" + String.format("%02d",   ((serverUptimeSeconds % 86400) % 3600 ) / 60).charAt(0));
            mTimerMinSecondDigit.setText("" + String.format("%02d",   ((serverUptimeSeconds % 86400) % 3600 ) / 60).charAt(1));
            mTimerSecondFirstDigit.setText("" + String.format("%02d",    ((serverUptimeSeconds % 86400) % 3600) % 60).charAt(0));
            mTimerSecondSecondDigit.setText("" + String.format("%02d",    ((serverUptimeSeconds % 86400) % 3600) % 60).charAt(1));

         String serverUptimeText =
                    String.format("%d days %d hours %d minutes %d seconds",
                            serverUptimeSeconds / 86400,
                            ( serverUptimeSeconds % 86400) / 3600 ,
                            ((serverUptimeSeconds % 86400) % 3600 ) / 60,
                            ((serverUptimeSeconds % 86400) % 3600) % 60
                    );

            Log.v("new its", "miliday"+serverUptimeText);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //counter.cancel();
    }

    public void chekForLocationPermissionRuntime(){
        if (Build.VERSION.SDK_INT>22){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                return;
            }

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
            }
        }
    }
}