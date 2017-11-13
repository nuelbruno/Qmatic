package ae.qmatic.tacme.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by mdev3 on 8/29/16.
 */
public class RemoteBookingBranchByIDModel {

    /**
     * id : 6
     * name : Abu Dhabi
     * customersWaiting : 10
     * openServicePoints : 0
     * maxWaitingTime : 4114
     * averageWaitingTime : 1844
     * totalWaitingTime : 18443
     * customersBeingServed : 0
     * parameter : {"openingHour":"07:30:00","closingHour":"14:30:00","description":"Abu Dhabi Branch","address1":"","address2":"","longitude":"12.017959999999979","address3":"","resetTime":"23:59","latitude":"57.637160000000094","postcode":"","city":"","country":""}
     * serviceDefiniionsList : [{"id":"20","bookingEnabled":"1","externalName":"API SERVICE","mobileEnabled":"1"},{"id":"27","bookingEnabled":"0","externalName":"ABD Service 0","mobileEnabled":"1"},{"id":"28","bookingEnabled":"0","externalName":"ABD Service 1","mobileEnabled":"1"},{"id":"29","bookingEnabled":"0","externalName":"ABD Service 2","mobileEnabled":"1"},{"id":"30","bookingEnabled":"0","externalName":"ABD Service 3","mobileEnabled":"0"},{"id":"31","bookingEnabled":"1","externalName":"ABD Service 4","mobileEnabled":"0"},{"id":"32","bookingEnabled":"1","externalName":"ABD Service 5","mobileEnabled":"1"}]
     */

    private String id;
    private String name;
    private String customersWaiting;
    private String openServicePoints;
    private String maxWaitingTime;
    private String averageWaitingTime;
    private String totalWaitingTime;
    private String customersBeingServed;
    /**
     * openingHour : 07:30:00
     * closingHour : 14:30:00
     * description : Abu Dhabi Branch
     * address1 :
     * address2 :
     * longitude : 12.017959999999979
     * address3 :
     * resetTime : 23:59
     * latitude : 57.637160000000094
     * postcode :
     * city :
     * country :
     */

    private ParameterEntity parameter;
    /**
     * id : 20
     * bookingEnabled : 1
     * externalName : API SERVICE
     * mobileEnabled : 1
     */

    private List<ServiceDefiniionsListEntity> serviceDefiniionsList;

    public static RemoteBookingBranchByIDModel objectFromData(String str) {

        return new Gson().fromJson(str, RemoteBookingBranchByIDModel.class);
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

    public String getCustomersWaiting() {
        return customersWaiting;
    }

    public void setCustomersWaiting(String customersWaiting) {
        this.customersWaiting = customersWaiting;
    }

    public String getOpenServicePoints() {
        return openServicePoints;
    }

    public void setOpenServicePoints(String openServicePoints) {
        this.openServicePoints = openServicePoints;
    }

    public String getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public void setMaxWaitingTime(String maxWaitingTime) {
        this.maxWaitingTime = maxWaitingTime;
    }

    public String getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(String averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public String getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(String totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public String getCustomersBeingServed() {
        return customersBeingServed;
    }

    public void setCustomersBeingServed(String customersBeingServed) {
        this.customersBeingServed = customersBeingServed;
    }

    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameter = parameter;
    }

    public List<ServiceDefiniionsListEntity> getServiceDefiniionsList() {
        return serviceDefiniionsList;
    }

    public void setServiceDefiniionsList(List<ServiceDefiniionsListEntity> serviceDefiniionsList) {
        this.serviceDefiniionsList = serviceDefiniionsList;
    }

    public static class ParameterEntity {
        private String openingHour;
        private String closingHour;
        private String description;
        private String address1;
        private String address2;
        private String longitude;
        private String address3;
        private String resetTime;
        private String latitude;
        private String postcode;
        private String city;
        private String country;

        public static ParameterEntity objectFromData(String str) {

            return new Gson().fromJson(str, ParameterEntity.class);
        }

        public String getOpeningHour() {
            return openingHour;
        }

        public void setOpeningHour(String openingHour) {
            this.openingHour = openingHour;
        }

        public String getClosingHour() {
            return closingHour;
        }

        public void setClosingHour(String closingHour) {
            this.closingHour = closingHour;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getResetTime() {
            return resetTime;
        }

        public void setResetTime(String resetTime) {
            this.resetTime = resetTime;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class ServiceDefiniionsListEntity {
        private String id;
        private String bookingEnabled;
        private String externalName;
        private String mobileEnabled;

        public static ServiceDefiniionsListEntity objectFromData(String str) {

            return new Gson().fromJson(str, ServiceDefiniionsListEntity.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBookingEnabled() {
            return bookingEnabled;
        }

        public void setBookingEnabled(String bookingEnabled) {
            this.bookingEnabled = bookingEnabled;
        }

        public String getExternalName() {
            return externalName;
        }

        public void setExternalName(String externalName) {
            this.externalName = externalName;
        }

        public String getMobileEnabled() {
            return mobileEnabled;
        }

        public void setMobileEnabled(String mobileEnabled) {
            this.mobileEnabled = mobileEnabled;
        }
    }
}

