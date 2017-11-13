package ae.qmatic.tacme.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by mdev3 on 8/15/16.
 */
public class ActivityConstant{

    public static String Appname ="Qmatic";
    public static int EXPECTEDTIME_CODE = 111;
    public static int BRANCH_CODE = 222;
    public static int SERVICE_CODE = 333;
    public static int DATE_PICKER_CODE = 444;
    public static int TIME_PICKER_CODE = 555;

    public static String EMIRATES_KEY = "emirates";
    public static String BRANCH_KEY = "branch";
    public static String SERVICE_KEY = "service";
    public static String APPOINTMENT_DATE_KEY = "appointment_date";
    public static String APPOINTMENT_TIME_KEY = "appointment_time";

    ProgressDialog pd = null;
    public void showProgressDialog(Context context, String msg){
        pd = new ProgressDialog(context);
        pd.setMessage(msg);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }
    public void hideProgressDialog(){
        if(pd!=null){
            pd.hide();
            pd=null;
        }
    }
}
