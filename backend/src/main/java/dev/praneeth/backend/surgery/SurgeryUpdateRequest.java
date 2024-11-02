package dev.praneeth.backend.Surgery;

import java.time.LocalDate;
import java.util.List;

public class SurgeryUpdateRequest {
    private LocalDate surgeryDate;
    private Surgery.SurgeryType surgeryType;
    private String outcome;
    private List<Integer> addPrescriptionIDs;
    private List<Integer> removePrescriptionIDs;
    private List<Integer> addDoctorIDs;
    private List<Integer> removeDoctorIDs;
    private String notes;

    // Getters and Setters for all fields
    public LocalDate getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(LocalDate surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public Surgery.SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(Surgery.SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<Integer> getAddPrescriptionIDs() {
        return addPrescriptionIDs;
    }

    public void setAddPrescriptionIDs(List<Integer> addPrescriptionIDs) {
        this.addPrescriptionIDs = addPrescriptionIDs;
    }

    public List<Integer> getRemovePrescriptionIDs() {
        return removePrescriptionIDs;
    }

    public void setRemovePrescriptionIDs(List<Integer> removePrescriptionIDs) {
        this.removePrescriptionIDs = removePrescriptionIDs;
    }

    public List<Integer> getAddDoctorIDs() {
        return addDoctorIDs;
    }

    public void setAddDoctorIDs(List<Integer> addDoctorIDs) {
        this.addDoctorIDs = addDoctorIDs;
    }

    public List<Integer> getRemoveDoctorIDs() {
        return removeDoctorIDs;
    }

    public void setRemoveDoctorIDs(List<Integer> removeDoctorIDs) {
        this.removeDoctorIDs = removeDoctorIDs;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
