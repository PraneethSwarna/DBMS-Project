package dev.praneeth.backend.Hospitalization;

import java.time.LocalDate;

public class Hospitalization {
    private Integer hospitalizationID; // Primary Key
    private LocalDate admissionDate;
    private LocalDate dischargeDate; // Nullable
    private String reason;
    private String notes;
    private Integer patientID; // Foreign Key to User
    private String roomNumber;

    // Constructors
    public Hospitalization() {}

    public Hospitalization(Integer hospitalizationID, LocalDate admissionDate, LocalDate dischargeDate, String reason, String notes, Integer patientID, String roomNumber) {
        this.hospitalizationID = hospitalizationID;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.reason = reason;
        this.notes = notes;
        this.patientID = patientID;
        this.roomNumber = roomNumber;
    }

    // Getters and Setters
    public Integer getHospitalizationID() {
        return hospitalizationID;
    }

    public void setHospitalizationID(Integer hospitalizationID) {
        this.hospitalizationID = hospitalizationID;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Hospitalization{" +
                "hospitalizationID=" + hospitalizationID +
                ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate +
                ", reason='" + reason + '\'' +
                ", notes='" + notes + '\'' +
                ", patientID=" + patientID +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
