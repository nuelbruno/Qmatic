package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 9/1/16.
 */
public class DeleteAppointment {


    /**
     * isDeleted : true
     * message : Appointment Deleted Sucessfully
     */

    private String isDeleted;
    private String message;

    public static DeleteAppointment objectFromData(String str) {

        return new Gson().fromJson(str, DeleteAppointment.class);
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
