package dev.praneeth.backend.LabResult;

import java.time.LocalDate;

public class LabResultUpdateRequest {

    private Double resultValue;
    private LocalDate testDate;
    private String notes;

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
}
