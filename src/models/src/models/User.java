package models;

import java.io. Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private boolean isAdmin;

    public User(String userId, String name, String password, String email, String phone, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean isAdmin() { return isAdmin; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "User ID: " + userId + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone;
    }
}
