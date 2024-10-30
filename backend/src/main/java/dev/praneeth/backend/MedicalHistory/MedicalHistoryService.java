package dev.praneeth.backend.MedicalHistory;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryService {

    private final MedicalHistoryDao medicalHistoryDao;

    public MedicalHistoryService(MedicalHistoryDao medicalHistoryDao) {
        this.medicalHistoryDao = medicalHistoryDao;
    }

    public List<MedicalHistory> getMedicalHistories() {
        return medicalHistoryDao.getAllMedicalHistories();
    }

    public void addMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistoryDao.addMedicalHistory(medicalHistory);
    }

    public void deleteMedicalHistory(Integer historyId) {
        Optional<MedicalHistory> existingHistory = medicalHistoryDao.getMedicalHistoryById(historyId);
        if (existingHistory.isEmpty()) {
            throw new IllegalStateException("Medical history with id " + historyId + " does not exist");
        }
        medicalHistoryDao.deleteMedicalHistory(historyId);
    }

    public List<MedicalHistory> getHistoriesByPatient(Integer patientId) {
        return medicalHistoryDao.getHistoriesByPatientId(patientId);
    }

    // New updateMedicalHistory method
    public void updateMedicalHistory(Integer historyId, MedicalHistoryUpdateRequest updateRequest) {
        Optional<MedicalHistory> existingHistory = medicalHistoryDao.getMedicalHistoryById(historyId);

        if (existingHistory.isEmpty()) {
            throw new IllegalStateException("Medical history with id " + historyId + " does not exist");
        }

        MedicalHistory medicalHistory = existingHistory.get();

        // Update fields if present in the request
        if (updateRequest.getPatientId() != null) {
            medicalHistory.setPatientID(updateRequest.getPatientId());
        }
        if (updateRequest.getDiagnosis() != null) {
            medicalHistory.setDiagnosis(updateRequest.getDiagnosis());
        }
        if (updateRequest.getTreatment() != null) {
            medicalHistory.setTreatment(updateRequest.getTreatment());
        }
        if (updateRequest.getNotes() != null) {
            medicalHistory.setNotes(updateRequest.getNotes());
        }

        // Update in the DAO
        medicalHistoryDao.updateMedicalHistory(medicalHistory);
    }
}
