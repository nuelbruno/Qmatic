package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/16/16.
 */
public class ServiceModel{


    /**
     * name : AK Service 0
     * id : 14
     * estimatedWait : 0
     * waitingTime : 0
     */

    private String name;
    private int id;
    private int estimatedWait;
    private int waitingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstimatedWait() {
        return estimatedWait;
    }

    public void setEstimatedWait(int estimatedWait) {
        this.estimatedWait = estimatedWait;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
