package dev.praneeth.backend.surgery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SurgeryService {

    private final SurgeryDao surgeryDao;

    public SurgeryService(SurgeryDao surgeryDao) {
        this.surgeryDao = surgeryDao;
    }

    // Get all surgeries
    public List<Surgery> getSurgeries() {
        return surgeryDao.getAllSurgeries();
    }

    // Add a new surgery
    public void addSurgery(Surgery surgery) {
        surgeryDao.addSurgery(surgery);
    }

    // Delete a surgery by ID
    public void deleteSurgery(Integer surgeryID) {
        Optional<Surgery> surgery = surgeryDao.getSurgeryById(surgeryID);
        if (surgery.isEmpty()) {
            throw new IllegalStateException("Surgery with ID " + surgeryID + " does not exist");
        }
        surgeryDao.deleteSurgery(surgeryID);
    }

    // Update an existing surgery
    @Transactional
    public void updateSurgery(Integer surgeryID, SurgeryUpdateRequest updateRequest) {
        Optional<Surgery> existingSurgery = surgeryDao.getSurgeryById(surgeryID);
        if (existingSurgery.isEmpty()) {
            throw new IllegalStateException("Surgery with ID " + surgeryID + " does not exist");
        }

        Surgery surgery = existingSurgery.get();

        // Update fields if present in request
        if (updateRequest.getSurgeryDate() != null) {
            surgery.setSurgeryDate(updateRequest.getSurgeryDate());
        }
        if (updateRequest.getSurgeryType() != null && !updateRequest.getSurgeryType().isEmpty()) {
            surgery.setSurgeryType(updateRequest.getSurgeryType());
        }
        if (updateRequest.getOutcome() != null && !updateRequest.getOutcome().isEmpty()) {
            surgery.setOutcome(updateRequest.getOutcome());
        }
        if (updateRequest.getPrescriptionID() != null && updateRequest.getPrescriptionID() > 0) {
            surgery.setPrescriptionID(updateRequest.getPrescriptionID());
        }
        if (updateRequest.getDoctorID() != null && updateRequest.getDoctorID() > 0) {
            surgery.setDoctorID(updateRequest.getDoctorID());
        }
        if (updateRequest.getNotes() != null && !updateRequest.getNotes().isEmpty()) {
            surgery.setNotes(updateRequest.getNotes());
        }

        surgeryDao.updateSurgery(surgeryID, surgery);
    }
}