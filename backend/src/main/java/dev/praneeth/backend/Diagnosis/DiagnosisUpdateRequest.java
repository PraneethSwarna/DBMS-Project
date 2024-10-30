package dev.praneeth.backend.Diagnosis;

public class DiagnosisUpdateRequest {

    private Integer prescriptionID;
    private Integer labTestID;
    private Integer labResultID;
    private String notes;

    // Getters and Setters
    public Integer getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(Integer prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public Integer getLabTestID() {
        return labTestID;
    }

    public void setLabTestID(Integer labTestID) {
        this.labTestID = labTestID;
    }

    public Integer getLabResultID() {
        return labResultID;
    }

    public void setLabResultID(Integer labResultID) {
        this.labResultID = labResultID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}