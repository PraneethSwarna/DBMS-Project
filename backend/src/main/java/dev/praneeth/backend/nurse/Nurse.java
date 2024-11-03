package dev.praneeth.backend.Nurse;

public class Nurse {

    private Integer nurseID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Shift shift;
    private String password;

    public enum Shift {
        Morning, Evening, Night
    }

    // Getters and Setters

    public Integer getNurseID() {
        return nurseID;
    }

    public void setNurseID(Integer nurseID) {
        this.nurseID = nurseID;
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

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
