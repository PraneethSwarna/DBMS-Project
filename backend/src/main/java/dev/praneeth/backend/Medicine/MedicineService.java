package dev.praneeth.backend.Medicine;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private final MedicineDao medicineDao;

    public MedicineService(MedicineDao medicineDao) {
        this.medicineDao = medicineDao;
    }

    // Get all medicines
    public List<Medicine> getMedicines() {
        return medicineDao.getAllMedicines();
    }

    // Get a medicine by ID
    public Optional<Medicine> getMedicineById(Integer medicineId) {
        return medicineDao.getMedicineById(medicineId);
    }

    // Add a new medicine
    public void addMedicine(Medicine medicine) {
        medicineDao.addMedicine(medicine);
    }

    // Delete a medicine by ID
    public void deleteMedicine(Integer medicineId) {
        medicineDao.getMedicineById(medicineId)
                .orElseThrow(() -> new IllegalStateException("Medicine with id " + medicineId + " does not exist"));
        medicineDao.deleteMedicine(medicineId);
    }

    // Update an existing medicine
    @Transactional
    public void updateMedicine(Integer medicineID, MedicineUpdateRequest updateRequest) {
        Optional<Medicine> existingMedicine = medicineDao.getMedicineById(medicineID);
        if (existingMedicine.isEmpty()) {
            throw new IllegalStateException("Medicine with ID " + medicineID + " does not exist");
        }

        Medicine medicine = existingMedicine.get();

        // Check for unique name if updating the name field
        if (updateRequest.getName() != null && !updateRequest.getName().isEmpty()) {
            Optional<Medicine> medicineWithSameName = medicineDao.getMedicineByName(updateRequest.getName());
            if (medicineWithSameName.isPresent() && !medicineWithSameName.get().getMedicineID().equals(medicineID)) {
                throw new IllegalStateException("Medicine with name " + updateRequest.getName() + " already exists");
            }
            medicine.setName(updateRequest.getName());
        }

        // Update other fields if present in request
        if (updateRequest.getDescription() != null && !updateRequest.getDescription().isEmpty()) {
            medicine.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getDosageForm() != null && !updateRequest.getDosageForm().isEmpty()) {
            medicine.setDosageForm(updateRequest.getDosageForm());
        }
        if (updateRequest.getStrength() != null && !updateRequest.getStrength().isEmpty()) {
            medicine.setStrength(updateRequest.getStrength());
        }

        medicineDao.updateMedicine(medicineID, medicine);
    }
}
