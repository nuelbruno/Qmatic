package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/16/16.
 */
public class ServiceNearByModel{

    /**
     * id : 5
     * name : TACME_BANGLORE
     * timeZone : Asia/Muscat
     * longitude : 55.53425259797626
     * latitude : 25.160528778029335
     * openTime : 00:00
     * closeTime : 00:00
     * estimatedWaitTime : 0
     * branchOpen : false
     * queuePassesClosingTime : false
     * longitudeE6 : 55534252
     * latitudeE6 : 25160528
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
