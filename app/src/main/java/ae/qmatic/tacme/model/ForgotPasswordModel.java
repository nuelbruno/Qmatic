package ae.qmatic.tacme.model;

/**
 * Created by nikunjkumar on 8/25/16.
 */
public class ForgotPasswordModel {

    /**
     * status : sucess
     * message : Please check your email
     */

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
