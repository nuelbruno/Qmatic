package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/29/16.
 */
public class RemoteBookingBranchesModel {


    /**
     * id : 6
     * name : Abu Dhabi
     * timeZone : Asia/Muscat
     * longitude : 12.017959999999979
     * latitude : 57.637160000000094
     * openTime : 07:30
     * closeTime : 14:30
     * estimatedWaitTime : 0
     * branchOpen : true
     * queuePassesClosingTime : false
     * longitudeE6 : 12017959
     * latitudeE6 : 57637160
     */

    private int id;
    private String name;
    private String timeZone;
    private double longitude;
    private double latitude;
    private String openTime;
    private String closeTime;
    private int estimatedWaitTime;
    private boolean branchOpen;
    private boolean queuePassesClosingTime;
    private int longitudeE6;
    private int latitudeE6;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public int getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(int estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public boolean isBranchOpen() {
        return branchOpen;
    }

    public void setBranchOpen(boolean branchOpen) {
        this.branchOpen = branchOpen;
    }

    public boolean isQueuePassesClosingTime() {
        return queuePassesClosingTime;
    }

    public void setQueuePassesClosingTime(boolean queuePassesClosingTime) {
        this.queuePassesClosingTime = queuePassesClosingTime;
    }

    public int getLongitudeE6() {
        return longitudeE6;
    }

    public void setLongitudeE6(int longitudeE6) {
        this.longitudeE6 = longitudeE6;
    }

    public int getLatitudeE6() {
        return latitudeE6;
    }

    public void setLatitudeE6(int latitudeE6) {
        this.latitudeE6 = latitudeE6;
    }
}
