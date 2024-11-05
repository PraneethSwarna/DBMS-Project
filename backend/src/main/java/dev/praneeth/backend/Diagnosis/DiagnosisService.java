package dev.praneeth.backend.Diagnosis;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {

    private final DiagnosisDao diagnosisDao;

    public DiagnosisService(DiagnosisDao diagnosisDao) {
        this.diagnosisDao = diagnosisDao;
    }

    // Get all diagnoses
    public List<Diagnosis> getDiagnoses() {
        return diagnosisDao.getAllDiagnoses();
    }

    // Get a diagnosis by ID
    public Optional<Diagnosis> getDiagnosisById(Integer diagnosisID) {
        return diagnosisDao.getDiagnosisById(diagnosisID);
    }

    // Add a new diagnosis
    public void addDiagnosis(Diagnosis diagnosis) {
        diagnosisDao.addDiagnosis(diagnosis);
    }

    // Delete a diagnosis by ID
    public void deleteDiagnosis(Integer diagnosisID) {
        Optional<Diagnosis> diagnosis = diagnosisDao.getDiagnosisById(diagnosisID);
        if (diagnosis.isEmpty()) {
            throw new IllegalStateException("Diagnosis with ID " + diagnosisID + " does not exist");
        }
        diagnosisDao.deleteDiagnosis(diagnosisID);
    }

    // Update an existing diagnosis
    @Transactional
    public void updateDiagnosis(Integer diagnosisID, DiagnosisUpdateRequest updateRequest) {
        // Retrieve the diagnosis or throw an exception if not found
        Diagnosis diagnosis = diagnosisDao.getDiagnosisById(diagnosisID)
                .orElseThrow(() -> new IllegalStateException("Diagnosis with ID " + diagnosisID + " does not exist."));

        // Update fields if provided
        if (updateRequest.getPrescriptionID() != null) {
            diagnosis.setPrescriptionID(updateRequest.getPrescriptionID());
        }
        if (updateRequest.getLabTestID() != null) {
            diagnosis.setLabTestID(updateRequest.getLabTestID());
        }
        if (updateRequest.getLabResultID() != null) {
            diagnosis.setLabResultID(updateRequest.getLabResultID());
        }
        if (updateRequest.getNotes() != null) {
            diagnosis.setNotes(updateRequest.getNotes());
        }

        // Save the updated diagnosis
        diagnosisDao.updateDiagnosis(diagnosisID, diagnosis);
    }
}