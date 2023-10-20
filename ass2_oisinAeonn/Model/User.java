package ass2_oisinAeonn.Model;

// Model for the user entity in DAH app, capturing core attributes of the user 

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String userType;
    private String profilePicture;

    // Constructor with all attributes

    public User(String username, String firstName, String lastName, String password, String userType, String profilePicture) {
        
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
        this.profilePicture = profilePicture;
    
    }

    // Secondary constructor for test class

    public User(String username2, String firstName2, String lastName2, String password2) {
    
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

    public String getProfilePicture() {
    
        return profilePicture;
    
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

    public void setProfilePicture(String profilePicture) {
    
        this.profilePicture = profilePicture;
    
    }

    // Tostring csv format with all attributes

    @Override
    
    public String toString() {
    
        return String.join(",", username, firstName, lastName, password, userType, profilePicture);
    
    }

}