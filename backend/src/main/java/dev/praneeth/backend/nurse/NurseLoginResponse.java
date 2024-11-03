package dev.praneeth.backend.Nurse;

public class NurseLoginResponse {
    private Integer nurseId;
    private String firstName;
    private String lastName;
    private String email;
    private String token; // Optional: for authentication token (JWT or similar)
    private String role;

    // Constructor
    public NurseLoginResponse(Integer nurseId, String firstName, String lastName, String email, String token, String role) {
        this.nurseId = nurseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public Integer getNurseId() {
        return nurseId;
    }

    public void setNurseId(Integer nurseId) {
        this.nurseId = nurseId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
