package dev.praneeth.backend.MedicalHistory;

import java.time.LocalDate;

public class MedicalHistory {
    private Integer historyID;
    private LocalDate recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
    private Integer patientID;

    // Constructors
    public MedicalHistory() {}

    public MedicalHistory(Integer historyID, LocalDate recordDate, String diagnosis, String treatment, String notes, Integer patientID) {
        this.historyID = historyID;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.patientID = patientID;
    }

    // Getters and Setters
    public Integer getHistoryID() {
        return historyID;
    }

    public void setHistoryID(Integer historyID) {
        this.historyID = historyID;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
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

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "historyID=" + historyID +
                ", recordDate=" + recordDate +
                ", diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                ", notes='" + notes + '\'' +
                ", patientID=" + patientID +
                '}';
    }
}
