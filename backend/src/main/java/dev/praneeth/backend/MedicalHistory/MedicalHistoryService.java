package dev.praneeth.backend.MedicalHistory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        medicalHistory.setRecordDate(LocalDate.now()); // Set recordDate to current date
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
        medicalHistory.setRecordDate(LocalDate.now()); // Set recordDate to current date

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

        medicalHistoryDao.updateMedicalHistory(medicalHistory);
    }

}
