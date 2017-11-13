package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 8/17/16.
 */
public class AppointmentResponseModel {


    /**
     * branchId : 97e4cb7fa19a059e36564241a56d8cadbfaed4a44abcfa540cf83b930d7e2c89
     * branchName : TACME
     * date : 2017-09-04
     * id : f932ac13d601ec049cbb67d94ce95d04ae63167458df9d84c93fca80e2f33b5c
     * serviceId : 80f16e2c6f310acff70e3a647a352797713f192957a31f5988fd4edfd5297d7a
     * serviceName : TAC Service 2
     * time : 07:40
     * title : Pending reservation
     */

    private AppointmentEntity appointment;

    public static AppointmentResponseModel objectFromData(String str) {

        return new Gson().fromJson(str, AppointmentResponseModel.class);
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    public static class AppointmentEntity {
        private String branchId;
        private String branchName;
        private String date;
        private String id;
        private String serviceId;
        private String serviceName;
        private String time;
        private String title;

        public static AppointmentEntity objectFromData(String str) {

            return new Gson().fromJson(str, AppointmentEntity.class);
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
