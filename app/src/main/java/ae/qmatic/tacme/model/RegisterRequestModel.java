package ae.qmatic.tacme.model;

/**
 * Created by nikunjkumar on 8/25/16.
 */
public class RegisterRequestModel {

    /**
     * id :
     * firstName : Akhil
     * lastName : Ajay
     * cardNumber :
     * properties : {"email":"kannamelathil@gmail.com","phoneMobile":"9496435058","password":"123456"}
     */

    private String id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    /**
     * email : kannamelathil@gmail.com
     * phoneMobile : 9496435058
     * password : 123456
     */

    private PropertiesBean properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    public static class PropertiesBean {
        private String email;
        private String phoneMobile;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneMobile() {
            return phoneMobile;
        }

        public void setPhoneMobile(String phoneMobile) {
            this.phoneMobile = phoneMobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
