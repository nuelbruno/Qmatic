package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 8/25/16.
 */
public class ConfirmAppointmentResponseModel {

    /**
     * branchId : 97e4cb7fa19a059e36564241a56d8cadbfaed4a44abcfa540cf83b930d7e2c89
     * branchName : TACME
     * customer : {"email":"aa@mm.com","firstName":"test","lastName":"test","phone":121212}
     * date : 2017-09-06
     * externalId : 0001630850
     * id : 77120b4558798bb96d9dbc6098f5c34dcc3c4501198ff8a699e2c921d86dc4e6
     * serviceId : d59a02f840a366404472dda70642f6d4d6ec0ab301ccf278d4dd67ba4babd055
     * serviceName : TAC Service 4
     * time : 08:50
     */

    private AppointmentEntity appointment;

    public static ConfirmAppointmentResponseModel objectFromData(String str) {

        return new Gson().fromJson(str, ConfirmAppointmentResponseModel.class);
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
        /**
         * email : aa@mm.com
         * firstName : test
         * lastName : test
         * phone : 121212
         */

        private CustomerEntity customer;
        private String date;
        private String externalId;
        private String id;
        private String serviceId;
        private String serviceName;
        private String time;

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

        public CustomerEntity getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerEntity customer) {
            this.customer = customer;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
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

        public static class CustomerEntity {
            private String email;
            private String firstName;
            private String lastName;
            private int phone;

            public static CustomerEntity objectFromData(String str) {

                return new Gson().fromJson(str, CustomerEntity.class);
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public int getPhone() {
                return phone;
            }

            public void setPhone(int phone) {
                this.phone = phone;
            }
        }
    }
}
