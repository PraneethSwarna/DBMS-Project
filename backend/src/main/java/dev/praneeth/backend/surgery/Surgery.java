package dev.praneeth.backend.Surgery;

import java.time.LocalDate;
import java.util.List;

public class Surgery {
    private Integer surgeryID;
    private LocalDate surgeryDate;
    private SurgeryType surgeryType;
    private String outcome;
    private List<Integer> prescriptionIDs;
    private String notes;
    private List<Integer> doctorIDs;
    private Integer patientID;

    // Constructors, Getters, and Setters
    public Surgery() {}

    public Surgery(LocalDate surgeryDate, SurgeryType surgeryType, String outcome, List<Integer> prescriptionIDs, String notes, List<Integer> doctorIDs) {
        this.surgeryDate = surgeryDate;
        this.surgeryType = surgeryType;
        this.outcome = outcome;
        this.prescriptionIDs = prescriptionIDs;
        this.notes = notes;
        this.doctorIDs = doctorIDs;
    }

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

    public SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<Integer> getPrescriptionIDs() {
        return prescriptionIDs;
    }

    public void setPrescriptionIDs(List<Integer> prescriptionIDs) {
        this.prescriptionIDs = prescriptionIDs;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Integer> getDoctorIDs() {
        return doctorIDs;
    }

    public void setDoctorIDs(List<Integer> doctorIDs) {
        this.doctorIDs = doctorIDs;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    @Override
    public String toString() {
        return "Surgery{" +
                "surgeryID=" + surgeryID +
                ", surgeryDate=" + surgeryDate +
                ", surgeryType=" + surgeryType +
                ", outcome='" + outcome + '\'' +
                ", prescriptionIDs=" + prescriptionIDs +
                ", notes='" + notes + '\'' +
                ", doctorIDs=" + doctorIDs +
                ", patientID=" + patientID +
                '}';
    }

    public enum SurgeryType {
        APPENDECTOMY,
        CHOLECYSTECTOMY,
        HERNIA_REPAIR,
        MASTECTOMY,
        C_SECTION,
        HIP_REPLACEMENT,
        KNEE_REPLACEMENT,
        CORONARY_ARTERY_BYPASS,
        GASTRIC_BYPASS,
        TONSILLECTOMY
    }
}
