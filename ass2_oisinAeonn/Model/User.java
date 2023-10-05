package ass2_oisinAeonn.Model;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private boolean vip;
    private boolean admin;

    // Constructors
    
    public User(String username, String firstName, String lastName, String password, boolean vip, boolean admin) {
    
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.vip = vip;
        this.admin = admin;
    
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

    public boolean isVip() {
    
        return vip;
    
    }

    public boolean isAdmin() {
    
        return admin;
    
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

    public void setVip(boolean vip) {
    
        this.vip = vip;
    
    }

    public void setAdmin(boolean admin) {
    
        this.admin = admin;
    
    }

    @Override
    
    public String toString() {
    
        return String.join(",", username, firstName, lastName, password, String.valueOf(vip), String.valueOf(admin));
    
    }

}
