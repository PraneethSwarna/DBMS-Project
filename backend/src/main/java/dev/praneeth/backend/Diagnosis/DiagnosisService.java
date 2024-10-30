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
        Optional<Diagnosis> existingDiagnosis = diagnosisDao.getDiagnosisById(diagnosisID);
        if (existingDiagnosis.isEmpty()) {
            throw new IllegalStateException("Diagnosis with ID " + diagnosisID + " does not exist");
        }

        Diagnosis diagnosis = existingDiagnosis.get();

        // Update fields if present in request
        if (updateRequest.getPrescriptionID() != null && updateRequest.getPrescriptionID() > 0) {
            diagnosis.setPrescriptionID(updateRequest.getPrescriptionID());
        }
        if (updateRequest.getLabTestID() != null && updateRequest.getLabTestID() > 0) {
            diagnosis.setLabTestID(updateRequest.getLabTestID());
        }
        if (updateRequest.getLabResultID() != null && updateRequest.getLabResultID() > 0) {
            diagnosis.setLabResultID(updateRequest.getLabResultID());
        }
        if (updateRequest.getNotes() != null && !updateRequest.getNotes().isEmpty()) {
            diagnosis.setNotes(updateRequest.getNotes());
        }

        diagnosisDao.updateDiagnosis(diagnosisID, diagnosis);
    }
}