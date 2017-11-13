package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 8/31/16.
 */
public class DeleteTicket {

    /**
     * isdeleted : false
     */

    private String isdeleted;

    public static DeleteTicket objectFromData(String str) {

        return new Gson().fromJson(str, DeleteTicket.class);
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }
}
