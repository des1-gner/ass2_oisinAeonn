package ass2_oisinAeonn.model;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String password;

    // Constructors
    
    public User(String username, String firstName, String lastName, String password) {
    
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password; 
    
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

    @Override
    
    public String toString() {
    
        return String.join(",", username, firstName, lastName, password);
    
    }

}