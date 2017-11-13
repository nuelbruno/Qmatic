package ae.qmatic.tacme.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.activity.BookingActivity;
import ae.qmatic.tacme.activity.MyRemoteBooking;
import ae.qmatic.tacme.model.DialogItem;


/**
 * Created by mdev3 on 3/8/16.
 */
public class DialogUtils {

    public static void doubleButtonDialog(Context mContext, String mMessage, final DialogListner mDialogListner) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.two_button_dialog);
        TextView message = (TextView) dialog.findViewById(R.id.yourMessage);
        message.setText(mMessage);
        Button noButton = (Button) dialog.findViewById(R.id.buttonCancel);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(null, Dialog.BUTTON_NEGATIVE);
                }
                dialog.dismiss();
            }
        });
        Button yesButton = (Button) dialog.findViewById(R.id.buttonProcess);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(null, Dialog.BUTTON_POSITIVE);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void singleButtonDialog(Context mContext, String mMessage, final DialogListner mDialogListner) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.single_button_dialog);
        TextView message = (TextView) dialog.findViewById(R.id.yourMessage);
        message.setText(mMessage);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(null, Dialog.BUTTON_NEUTRAL);

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void singleButtonDialogCancel(Context mContext, String mMessage, final DialogListner mDialogListner) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.single_button_dialog);
        TextView message = (TextView) dialog.findViewById(R.id.yourMessage);
        message.setText(mMessage);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(null, 10003);

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void dialogList(Activity mContext, final String title, final List<DialogItem> mList, final DialogListner mDialogListner, final int requestCode) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_list_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView mTitleView = (TextView) dialog.findViewById(R.id.DialogTitle);
        mTitleView.setText(title);
        ListView mListView = (ListView) dialog.findViewById(R.id.dialogList);
        DialogListAdapter mAdapter = new DialogListAdapter(mContext, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedItem(mList.get(position), requestCode);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void dialogConfirmInfoPopup(final Activity mContext, final HashMap<String, String> mHashMap, final DialogListner mDialogListner){
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_confirm);
        dialog.setCancelable(true);
        //dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView mDateView = (TextView) dialog.findViewById(R.id.DateView);
        mDateView.setText(mHashMap.get("DATE").toString());
        TextView mTimeView = (TextView) dialog.findViewById(R.id.TimeView);
        mTimeView.setText(mHashMap.get("TIME").toString());

        TextView mBranchNameView = (TextView) dialog.findViewById(R.id.BranchNameView);
        mBranchNameView.setText(mHashMap.get("BRANCH").toString());
        TextView mServiceNameView = (TextView) dialog.findViewById(R.id.ServiceNameView);
        mServiceNameView.setText(mHashMap.get("SERVICE").toString());


        Button btnBook = (Button) dialog.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedHashMap(mHashMap);
                }
                dialog.dismiss();

            }
        });

        Button btnPrevious = (Button) dialog.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ////// access the Textview for getting user provided Remote booking information
        /*TextView textviewBookedEmirates = (TextView)dialog.findViewById(R.id.textviewBookedEmirates);
        TextView textviewBookedBranch = (TextView)dialog.findViewById(R.id.textviewBookedBranch);
        TextView textviewBookedService = (TextView)dialog.findViewById(R.id.textviewBookedService);
        TextView textviewBookedDate = (TextView)dialog.findViewById(R.id.textviewBookedDate);
        TextView textviewBookedTime = (TextView)dialog.findViewById(R.id.textviewBookedTime);
        textviewBookedEmirates.setText(userAppointmentData.get(ActivityConstant.EMIRATES_KEY));
        textviewBookedBranch.setText(userAppointmentData.get(ActivityConstant.BRANCH_KEY));
        textviewBookedService.setText(userAppointmentData.get(ActivityConstant.SERVICE_KEY));
        textviewBookedDate.setText(userAppointmentData.get(ActivityConstant.APPOINTMENT_DATE_KEY));
        textviewBookedTime.setText(userAppointmentData.get(ActivityConstant.APPOINTMENT_TIME_KEY));*/


        dialog.show();
    }

    public static void dialogAppointmentConfirmed(Activity mContext, final HashMap mHashMap,final DialogListner mDialogListner) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_confirmed_qrcode);
        dialog.setCancelable(true);
        //dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView mDateView = (TextView) dialog.findViewById(R.id.AppointmentDateView);
        mDateView.setText(mHashMap.get("DATE").toString());
        TextView mTimeView = (TextView) dialog.findViewById(R.id.AppointTimeView);
        mTimeView.setText(mHashMap.get("TIME").toString());

        TextView mBranchNameView = (TextView) dialog.findViewById(R.id.BranchNameView);
        mBranchNameView.setText(mHashMap.get("BRANCH").toString());
        TextView mServiceNameView = (TextView) dialog.findViewById(R.id.ServiceNameView);
        mServiceNameView.setText(mHashMap.get("SERVICE").toString());
        Button btnRechedule = (Button) dialog.findViewById(R.id.btnRechedule);
        btnRechedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(mHashMap.get("IDS").toString(),10002);
                }
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mDialogListner != null) {
                    mDialogListner.mDialogSelectedType(mHashMap.get("IDS").toString(),10001);
                }
            }
        });
        dialog.show();

    }

    public static void dialogRemoteBookingConfirmed(Activity mContext/*,final List<String> mList, final DialogListner mDialogListner*/) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.7);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remote_booking_confirm_qrcode);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.CENTER);
        ImageView imageViewClose = (ImageView)dialog.findViewById(R.id.imageViewClose);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public static void dialogAppointmentDate(Activity mContext/*,final List<String> mList, final DialogListner mDialogListner*/) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_confirmed_qrcode);
        dialog.setCancelable(true);
        //dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

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
            }
        });
        dialog.show();

    }

    public static void dialogDatePicker(final Activity mContext, final DialogListner mDialogListner) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.7);
        final Dialog dialog = new Dialog(mContext, R.style.DialogTimePicker);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_picker_layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // date picker dialog
        final CalendarView calendarView = (CalendarView) dialog.findViewById(R.id.dtpkr);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int selMonth = month + 1;
                String selectedDate = dayOfMonth + "/" + selMonth + "/" + year;
                calendarView.setTag(selectedDate);
            }
        });
        /// Select/Done button and even listener
        Button btnSelectDate = (Button) dialog.findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogListner != null) {
                    String obj = (String) calendarView.getTag();
                    if (obj!=null)
                        mDialogListner.mDialogSelectedType(obj, ActivityConstant.DATE_PICKER_CODE);
                    else{
                        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        mDialogListner.mDialogSelectedType(date, ActivityConstant.DATE_PICKER_CODE);
                    }
                }
                dialog.dismiss();
            }
        });
        /// Cancel button and Listener - Dismiss dialog
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelDateSelection);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void dialogAppointmentTimePicker(final Activity mContext, final DialogListner mDialogListner) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.8);
        final Dialog dialog = new Dialog(mContext, R.style.DialogTimePicker);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.time_picker_layout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        // Time picker dialog
        final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);

        Button btnButtonDone = (Button) dialog.findViewById(R.id.btnSelectTime);
        Button btnCancelTimeSelection = (Button) dialog.findViewById(R.id.btnCancelTimeSelection);
        //// dismiss the dialog
        btnCancelTimeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        /// passs selected time and pass it.
        btnButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogListner != null) {
                    int hour = timePicker.getCurrentHour();
                    String mins = "";
                    String am_pm = "";
                    Log.d("Hour:"+ hour,    "Minute: "+timePicker.getCurrentMinute(), null);
                    if(hour > 12){
                        am_pm = " PM";
                        hour = hour - 12;
                    }
                    else{
                        am_pm = " AM";
                    }
                    if (timePicker.getCurrentMinute()<10) {
                        mins = "0"+timePicker.getCurrentMinute();
                    }
                    else {
                        mins = String.valueOf(timePicker.getCurrentMinute());
                    }
                    Log.d("Hour:" + hour, "Minute: " + mins + am_pm, null);
                    StringBuilder finalTime = new StringBuilder().append(hour).append(":").append(mins).append(am_pm);
                    mDialogListner.mDialogSelectedType(finalTime.toString(), ActivityConstant.TIME_PICKER_CODE);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public static void dialogBranchDetail(final Activity mContext, HashMap<String, Object> bData, final DialogListner mDialogListner){
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.9);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.branch_detail);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        ////// Access required Textview and set Branch Deatil value on it.
        TextView txtBranchName = (TextView)dialog.findViewById(R.id.txtViewBranchName);
        TextView txtviewBranchAddress = (TextView)dialog.findViewById(R.id.txtviewBranchAddress);
        TextView txtviewBranchCallNo = (TextView)dialog.findViewById(R.id.txtviewBranchCallNo);
        TextView txtviewBranchStatus = (TextView)dialog.findViewById(R.id.txtviewBranchStatus);
        TextView txtviewBranchWorkingDays = (TextView)dialog.findViewById(R.id.txtviewBranchWorkingDays);
        TextView txtviewBranchWorkingTime = (TextView)dialog.findViewById(R.id.txtviewBranchWorkingTime);
        ///// set values
        txtBranchName.setText(bData.get("branch_name").toString());
        txtviewBranchAddress.setText("branch_address");
        txtviewBranchCallNo.setText("NO NUMBER");
        txtviewBranchStatus.setText((Boolean) bData.get("branch_status") ? "Open" : "Close");
        txtviewBranchWorkingDays.setText(bData.get("branch_working_days").toString());
        txtviewBranchWorkingTime.setText(bData.get("branch_timing").toString());

        Button btnRemoteBooking = (Button) dialog.findViewById(R.id.btnRemoteBooking);
        Button btnBookAnAppointment = (Button) dialog.findViewById(R.id.btnBookAnAppointment);
        btnRemoteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MyRemoteBooking.class));
                dialog.hide();
            }
        });

        btnBookAnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BookingActivity.class));
                dialog.hide();
            }
        });


        dialog.show();
    }

    public static void dialogAppointmentDetails(Activity mContext/*,final List<String> mList, final DialogListner mDialogListner*/) {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //int height = (int) (metrics.heightPixels * 0.6);
        final Dialog dialog = new Dialog(mContext, R.style.DialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_details);
        dialog.setCancelable(true);
        //dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        Button btnRechedule = (Button) dialog.findViewById(R.id.btnCacelAppointment);
        btnRechedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public interface DialogListner {
        void mDialogSelectedType(String mMesage, int i);

        void mDialogSelectedItem(DialogItem mDialogItem, int i);

        void mDialogSelectedHashMap(HashMap mHashMap);
    }

}
