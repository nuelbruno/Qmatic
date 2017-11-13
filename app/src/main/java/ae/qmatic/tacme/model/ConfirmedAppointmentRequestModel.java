package ae.qmatic.tacme.model;

import com.google.gson.Gson;

/**
 * Created by mdev3 on 8/25/16.
 */
public class ConfirmedAppointmentRequestModel {
    /**
     * customer : {"firstName":"test","lastName":"test","email":"aa@mm.com","phone":"121212"}
     */

    private AppointmentEntity appointment;

    public static ConfirmedAppointmentRequestModel objectFromData(String str) {

        return new Gson().fromJson(str, ConfirmedAppointmentRequestModel.class);
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    public static class AppointmentEntity {
        /**
         * firstName : test
         * lastName : test
         * email : aa@mm.com
         * phone : 121212
         */

        private CustomerEntity customer;

        public static AppointmentEntity objectFromData(String str) {

            return new Gson().fromJson(str, AppointmentEntity.class);
        }

        public CustomerEntity getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerEntity customer) {
            this.customer = customer;
        }

        public static class CustomerEntity {
            private String firstName;
            private String lastName;
            private String email;
            private String phone;

            public static CustomerEntity objectFromData(String str) {

                return new Gson().fromJson(str, CustomerEntity.class);
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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }


//    /**
//     * customer : {"firstName":"Hrithik","lastName":"Roshan","dateOfBirth":"2017-08-31","email":"aa@mm.com","phone":"121212"}
//     * notes : notenote
//     */
//
//    private AppointmentEntity appointment;
//
//    public AppointmentEntity getAppointment() {
//        return appointment;
//    }
//
//    public void setAppointment(AppointmentEntity appointment) {
//        this.appointment = appointment;
//    }
//
//    public static class AppointmentEntity {
//        /**
//         * firstName : Hrithik
//         * lastName : Roshan
//         * dateOfBirth : 2017-08-31
//         * email : aa@mm.com
//         * phone : 121212
//         */
//
//        private CustomerEntity customer;
//        private String notes;
//
//        public CustomerEntity getCustomer() {
//            return customer;
//        }
//
//        public void setCustomer(CustomerEntity customer) {
//            this.customer = customer;
//        }
//
//        public String getNotes() {
//            return notes;
//        }
//
//        public void setNotes(String notes) {
//            this.notes = notes;
//        }
//
//        public static class CustomerEntity {
//            private String firstName;
//            private String lastName;
//            private String dateOfBirth;
//            private String email;
//            private String phone;
//
//            public String getFirstName() {
//                return firstName;
//            }
//
//            public void setFirstName(String firstName) {
//                this.firstName = firstName;
//            }
//
//            public String getLastName() {
//                return lastName;
//            }
//
//            public void setLastName(String lastName) {
//                this.lastName = lastName;
//            }
//
//            public String getDateOfBirth() {
//                return dateOfBirth;
//            }
//
//            public void setDateOfBirth(String dateOfBirth) {
//                this.dateOfBirth = dateOfBirth;
//            }
//
//            public String getEmail() {
//                return email;
//            }
//
//            public void setEmail(String email) {
//                this.email = email;
//            }
//
//            public String getPhone() {
//                return phone;
//            }
//
//            public void setPhone(String phone) {
//                this.phone = phone;
//            }
//        }
//    }
}
