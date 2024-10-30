package dev.praneeth.backend.Prescription;

import java.time.LocalDate;
import java.util.List;

public class Prescription {
    private Integer prescriptionID;
    private LocalDate prescriptionDate;
    private List<PrescriptionMedicine> medicines;

    // Getters and Setters
    public Integer getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(Integer prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<PrescriptionMedicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<PrescriptionMedicine> medicines) {
        this.medicines = medicines;
    }
}
