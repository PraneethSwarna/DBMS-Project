package dev.praneeth.backend.Prescription;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionUpdateRequest {
    private LocalDate prescriptionDate;
    private List<MedicineUpdateRequest> medicines; // Existing medicines (update or delete)
    private List<MedicineUpdateRequest> newMedicines; // New medicines to be added

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<MedicineUpdateRequest> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineUpdateRequest> medicines) {
        this.medicines = medicines;
    }

    public List<MedicineUpdateRequest> getNewMedicines() {
        return newMedicines;
    }

    public void setNewMedicines(List<MedicineUpdateRequest> newMedicines) {
        this.newMedicines = newMedicines;
    }
}
