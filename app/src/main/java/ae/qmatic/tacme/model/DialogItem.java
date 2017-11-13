package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/16/16.
 */
public class DialogItem {
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String Id ="";
    private String name ="";
}
