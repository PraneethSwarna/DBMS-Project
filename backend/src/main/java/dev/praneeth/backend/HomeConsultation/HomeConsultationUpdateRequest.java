package dev.praneeth.backend.HomeConsultation;

import java.time.LocalDate;
import java.time.LocalTime;

public class HomeConsultationUpdateRequest {
    private LocalDate consultationDate;
    private LocalTime consultationTime;
    private String outcome;
    private Integer doctorID;
    private Integer patientID;
    private String notes;

    // Constructors
    public HomeConsultationUpdateRequest() {}

    public HomeConsultationUpdateRequest(LocalDate consultationDate, LocalTime consultationTime, String outcome, Integer doctorID, Integer patientID, String notes) {
        this.consultationDate = consultationDate;
        this.consultationTime = consultationTime;
        this.outcome = outcome;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.notes = notes;
    }

    // Getters and Setters
    public LocalDate getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDate consultationDate) {
        this.consultationDate = consultationDate;
    }

    public LocalTime getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(LocalTime consultationTime) {
        this.consultationTime = consultationTime;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
