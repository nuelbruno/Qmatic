package ae.qmatic.tacme.model;

/**
 * Created by nikunjkumar on 8/25/16.
 */
public class RegisterResponseModel {

    /**
     * status : sucess
     * message : User Registered Sucessfully
     * object : {"firstName":"oyffoyy","lastName":"itdididt","id":"65","properties":{"phoneMobile":"9865321478","email":"test1@gmail.com","password":"test"}}
     */

    private String status;
    private String message;
    /**
     * firstName : oyffoyy
     * lastName : itdididt
     * id : 65
     * properties : {"phoneMobile":"9865321478","email":"test1@gmail.com","password":"test"}
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
        private String id;
        /**
         * phoneMobile : 9865321478
         * email : test1@gmail.com
         * password : test
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
        }
    }
}
