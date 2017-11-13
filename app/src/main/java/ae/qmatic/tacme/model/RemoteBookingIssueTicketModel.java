package ae.qmatic.tacme.model;

/**
 * Created by mdev3 on 8/29/16.
 */
public class RemoteBookingIssueTicketModel {


    /**
     * clientId : RTFP-HHPG-BABQ-PQLW-EQCE-MHGZ-NNHS
     * serviceId : 14
     * branchId : 4
     * queueId : 17
     * ticketNumber : A4501
     * visitId : 91211
     */

    private String clientId;
    private int serviceId;
    private int branchId;
    private int queueId;
    private String ticketNumber;
    private int visitId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }
}
