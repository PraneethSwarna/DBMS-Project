package dev.praneeth.backend.LabResult;

import java.time.LocalDate;

public class LabResult {

    private Integer labResultID;
    private Double resultValue;
    private LocalDate testDate;
    private String notes;

    // Constructors
    public LabResult() {}

    public LabResult(Double resultValue, LocalDate testDate, String notes) {
        this.resultValue = resultValue;
        this.testDate = testDate;
        this.notes = notes;
    }

    // Getters and Setters
    public Integer getLabResultID() {
        return labResultID;
    }

    public void setLabResultID(Integer labResultID) {
        this.labResultID = labResultID;
    }

    public Double getResultValue() {
        return resultValue;
    }

    public void setResultValue(Double resultValue) {
        this.resultValue = resultValue;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "LabResult{" +
                "labResultID=" + labResultID +
                ", resultValue=" + resultValue +
                ", testDate=" + testDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}

