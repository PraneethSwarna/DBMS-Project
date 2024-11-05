package dev.praneeth.backend.Prescription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;

    public PrescriptionService(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptionDao.getAllPrescriptions();
    }

    public Optional<Prescription> getPrescriptionById(Integer prescriptionID) {
        return prescriptionDao.getPrescriptionById(prescriptionID);
    }

    public void addPrescription(Prescription prescription) {
        prescriptionDao.addPrescription(prescription);
    }

    public void deletePrescription(Integer prescriptionID) {
        Optional<Prescription> prescription = prescriptionDao.getPrescriptionById(prescriptionID);
        if (prescription.isEmpty()) {
            throw new IllegalStateException("Prescription with ID " + prescriptionID + " does not exist");
        }
        prescriptionDao.deletePrescription(prescriptionID);
    }

    @Transactional
    public void updatePrescription(Integer prescriptionID, PrescriptionUpdateRequest updateRequest) {
        Optional<Prescription> existingPrescription = prescriptionDao.getPrescriptionById(prescriptionID);
        if (existingPrescription.isEmpty()) {
            throw new IllegalStateException("Prescription with ID " + prescriptionID + " does not exist");
        }

        // Update the prescription in the DAO
        prescriptionDao.updatePrescription(prescriptionID, updateRequest);
    }
}
