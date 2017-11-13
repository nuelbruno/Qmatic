package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by nikunjkumar on 8/17/16.
 */
public class GetAllBranches {
    /**
     * id : 3
     * qpid : 5
     * publicId : 1ef6e6d8e10755d40ca9d0a5792a3f75d7c4407519f7dfdef53cf7c02e4a82dc
     * name : AJMAN
     * latitude : 25.160528778029335
     * longitude : 55.53425259797626
     */

    private String id;

    private String name;


    public static GetAllBranches objectFromData(String str) {

        return new Gson().fromJson(str, GetAllBranches.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
