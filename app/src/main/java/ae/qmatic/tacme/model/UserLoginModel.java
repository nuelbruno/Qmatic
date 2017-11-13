package ae.qmatic.tacme.model;

/**
 * Created by nikunjkumar on 8/25/16.
 */
public class UserLoginModel {


    /**
     * status : sucess
     * message : Sucessfully logged in
     * object : {"firstName":"aaaas","lastName":"ddddas","properties":{"phoneMobile":"12312312","email":"ffffas@gmail.com","password":"123123","customerId":"62"}}
     */

    private String status;
    private String message;
    /**
     * firstName : aaaas
     * lastName : ddddas
     * properties : {"phoneMobile":"12312312","email":"ffffas@gmail.com","password":"123123","customerId":"62"}
     */

    private ObjectBean object;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        private String firstName;
        private String lastName;
        /**
         * phoneMobile : 12312312
         * email : ffffas@gmail.com
         * password : 123123
         * customerId : 62
         */

        private PropertiesBean properties;

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

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class PropertiesBean {
            private String phoneMobile;
            private String email;
            private String password;
            private String customerId;

            public String getPhoneMobile() {
                return phoneMobile;
            }

            public void setPhoneMobile(String phoneMobile) {
                this.phoneMobile = phoneMobile;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }
        }
    }
}
