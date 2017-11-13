package ae.qmatic.tacme.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mdev3 on 8/18/16.
 */
public class DateUtils {

    public static String getDate(String mDateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
        mDateStr=mDateStr.replace("T"," ");
        String formattedDate="";
        try {
            Date date = null;
            date = sdf.parse(mDateStr);
            formattedDate = targetFormat.format(date);
            Log.e("FormattedDate", "" + formattedDate);
        }catch (Exception e){
            Log.e("Exception", "" + e.toString());
        }
        return formattedDate;
    }

    public static String getTime(String mDateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat TimeFormat = new SimpleDateFormat("hh:mm a");
        mDateStr=mDateStr.replace("T"," ");
        String formattedDate="";
        try{
            Date date = null;
            date = sdf.parse(mDateStr);
            formattedDate = TimeFormat.format(date);
            Log.e("FormattedTime", "" + formattedDate);

        }catch(Exception e){

        }
        return formattedDate;
    }

}
