package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 8/18/16.
 */
public class ServiceListModel {


    /**
     * id : 6e4e27211c91baee3c77082665bbb4bc5e83e0875c383b2c592bcbd0e2e60b19
     * name : TAC Service 5
     */

    private String id;
    private String name;

    public static ServiceListModel objectFromData(String str) {

        return new Gson().fromJson(str, ServiceListModel.class);
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
