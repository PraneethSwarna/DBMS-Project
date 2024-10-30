package dev.praneeth.backend.surgery;

import java.time.LocalDate;

public class Surgery {
    private Integer surgeryID;
    private LocalDate surgeryDate;
    private String surgeryType;
    private String outcome;
    private Integer prescriptionID;
    private String notes;
    private Integer doctorID;

    // Constructors
    public Surgery() {}

    public Surgery(LocalDate surgeryDate, String surgeryType, String outcome, Integer prescriptionID, String notes, Integer doctorID) {
        this.surgeryDate = surgeryDate;
        this.surgeryType = surgeryType;
        this.outcome = outcome;
        this.prescriptionID = prescriptionID;
        this.notes = notes;
        this.doctorID = doctorID;
    }

    // Getters and Setters
    public Integer getSurgeryID() {
        return surgeryID;
    }

    public void setSurgeryID(Integer surgeryID) {
        this.surgeryID = surgeryID;
    }

    public LocalDate getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(LocalDate surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(Integer prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    @Override
    public String toString() {
        return "Surgery{" +
                "surgeryID=" + surgeryID +
                ", surgeryDate=" + surgeryDate +
                ", surgeryType='" + surgeryType + '\'' +
                ", outcome='" + outcome + '\'' +
                ", prescriptionID=" + prescriptionID +
                ", notes='" + notes + '\'' +
                ", doctorID=" + doctorID +
                '}';
    }
}
