package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/30/16.
 */
public class RemoteBookingListModel {
    private String service_id = "";
    private String service_name = "";

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String branch_id = "";
    private String branch_name = "";
    private String ticket ="";
    private String time= "";
    private String color = "";

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status = "";

    public String getWaitingQueue() {
        return waitingQueue;
    }

    public void setWaitingQueue(String waitingQueue) {
        this.waitingQueue = waitingQueue;
    }

    public String getAverageServingTime() {
        return averageServingTime;
    }

    public void setAverageServingTime(String averageServingTime) {
        this.averageServingTime = averageServingTime;
    }

    private String waitingQueue = "";
    private String averageServingTime = "";

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    private String visitId= "";
}
