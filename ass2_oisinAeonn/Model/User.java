package ass2_oisinAeonn.Model;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String userType;

    // Constructors
    public User(String username, String firstName, String lastName, String password, String userType) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return String.join(",", username, firstName, lastName, password, userType);
    }
}
