package dev.praneeth.backend.Hospitalization;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalizationService {

    private final HospitalizationDao hospitalizationDao;

    public HospitalizationService(HospitalizationDao hospitalizationDao) {
        this.hospitalizationDao = hospitalizationDao;
    }

    // Retrieve all hospitalizations
    public List<Hospitalization> getHospitalizations() {
        return hospitalizationDao.getAllHospitalizations();
    }

    // Retrieve hospitalizations by patient ID
    public List<Hospitalization> getHospitalizationByPatient(Integer patientID) {
        return hospitalizationDao.getHospitalizationByPatient(patientID);
    }

    // Retrieve hospitalizations by patient ID
    public Optional<Hospitalization> getHospitalizationById(Integer patientID) {
        return hospitalizationDao.getHospitalizationById(patientID);
    }

    // Add a new hospitalization
    public void addHospitalization(Hospitalization hospitalization) {
        Optional<Hospitalization> existingHospitalization = hospitalizationDao.getHospitalizationById(hospitalization.getHospitalizationID());
        if (existingHospitalization.isPresent()) {
            throw new IllegalStateException("Hospitalization already exists");
        }
        hospitalizationDao.addHospitalization(hospitalization);
    }

    // Delete a hospitalization by ID
    public void deleteHospitalization(Integer hospitalizationId) {
        boolean exists = hospitalizationDao.getHospitalizationById(hospitalizationId).isPresent();
        if (!exists) {
            throw new IllegalStateException("Hospitalization with ID " + hospitalizationId + " does not exist.");
        }
        hospitalizationDao.deleteHospitalization(hospitalizationId);
    }

    // Update a hospitalization by ID
    @Transactional
    public void updateHospitalization(Integer hospitalizationId, HospitalizationUpdateRequest updateRequest) {
        // Retrieve the hospitalization or throw an exception if not found
        Hospitalization hospitalization = hospitalizationDao.getHospitalizationById(hospitalizationId)
                .orElseThrow(() -> new IllegalStateException("Hospitalization with ID " + hospitalizationId + " does not exist."));

        // Update fields if provided
        if (updateRequest.getAdmissionDate() != null) {
            hospitalization.setAdmissionDate(updateRequest.getAdmissionDate());
        }
        if (updateRequest.getDischargeDate() != null) {
            hospitalization.setDischargeDate(updateRequest.getDischargeDate());
        }
        if (updateRequest.getReason() != null) {
            hospitalization.setReason(updateRequest.getReason());
        }
        if (updateRequest.getNotes() != null) {
            hospitalization.setNotes(updateRequest.getNotes());
        }
        if (updateRequest.getPatientID() != null) {
            hospitalization.setPatientID(updateRequest.getPatientID());
        }
        if (updateRequest.getRoomNumber() != null) {
            hospitalization.setRoomNumber(updateRequest.getRoomNumber());
        }

        // Save the updated hospitalization
        hospitalizationDao.updateHospitalization(hospitalization);
    }
}
