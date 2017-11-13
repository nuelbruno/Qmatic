package ae.qmatic.tacme.utils;

import android.util.Base64;

/**
 * Created by mdev3 on 8/16/16.
 */
public interface HttpConstants {
     //String BASE_URL = "http://192.168.1.247:8080/qsystem/";
     String BASE_URL = "http://192.168.1.247:18080/";

     //String BASE_URL = "http://192.168.1.247:18080/qwebbook/"; - for getting dates and time

     String BASEURL2= "http://192.168.1.247:8080/qsystem/rest/";
     String auth = "Basic " + Base64.encodeToString(("mobile" + ":" + "ulan").getBytes(),Base64.NO_WRAP);
     String auth2 = "Basic " + Base64.encodeToString(("superadmin" + ":" + "ulan").getBytes(),Base64.NO_WRAP);
}
