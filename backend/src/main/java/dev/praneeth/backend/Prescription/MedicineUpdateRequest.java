package dev.praneeth.backend.Prescription;

public class MedicineUpdateRequest {
    private Integer originalMedicineID; // Required for identification
    private Integer newMedicineID;       // New ID if changing the medicine
    private String dosage;                // Optional
    private String frequency;             // Optional
    private Integer duration;             // Optional
    private String instructions;          // Optional
    private boolean delete;               // Flag to indicate deletion

    // Getters and Setters
    public Integer getOriginalMedicineID() {
        return originalMedicineID;
    }

    public void setOriginalMedicineID(Integer originalMedicineID) {
        this.originalMedicineID = originalMedicineID;
    }

    public Integer getNewMedicineID() {
        return newMedicineID;
    }

    public void setNewMedicineID(Integer newMedicineID) {
        this.newMedicineID = newMedicineID;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}

