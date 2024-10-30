package dev.praneeth.backend.Nurse;

public class NurseUpdateRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Nurse.Shift shift;

    // Getters and Setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Nurse.Shift getShift() {
        return shift;
    }

    public void setShift(Nurse.Shift shift) {
        this.shift = shift;
    }
}
