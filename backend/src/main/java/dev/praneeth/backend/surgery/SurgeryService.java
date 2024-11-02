package dev.praneeth.backend.Surgery;

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

    public List<Surgery> getSurgeries() {
        return surgeryDao.getSurgeries();
    }

    @Transactional
    public void addSurgery(Surgery surgery) {
        surgeryDao.addSurgery(surgery);
    }

    public void deleteSurgery(Integer surgeryID) {
        Optional<Surgery> surgery = surgeryDao.getSurgeryById(surgeryID);
        if (surgery.isEmpty()) {
            throw new IllegalStateException("Surgery with ID " + surgeryID + " does not exist");
        }
        surgeryDao.deleteSurgery(surgeryID);
    }

    @Transactional
    public void updateSurgery(Integer surgeryID, SurgeryUpdateRequest updateRequest) {
        Optional<Surgery> existingSurgery = surgeryDao.getSurgeryById(surgeryID);
        if (existingSurgery.isEmpty()) {
            throw new IllegalStateException("Surgery with ID " + surgeryID + " does not exist");
        }

        Surgery surgery = existingSurgery.get();

        if (updateRequest.getSurgeryDate() != null) {
            surgery.setSurgeryDate(updateRequest.getSurgeryDate());
        }
        if (updateRequest.getSurgeryType() != null) {
            surgery.setSurgeryType(updateRequest.getSurgeryType());
        }
        if (updateRequest.getOutcome() != null) {
            surgery.setOutcome(updateRequest.getOutcome());
        }
        if (updateRequest.getNotes() != null) {
            surgery.setNotes(updateRequest.getNotes());
        }

        surgeryDao.updateSurgery(surgeryID, surgery);

        if (updateRequest.getAddDoctorIDs() != null || updateRequest.getRemoveDoctorIDs() != null) {
            List<Integer> addDoctorIDs = updateRequest.getAddDoctorIDs() != null ? updateRequest.getAddDoctorIDs() : List.of();
            List<Integer> removeDoctorIDs = updateRequest.getRemoveDoctorIDs() != null ? updateRequest.getRemoveDoctorIDs() : List.of();
            surgeryDao.updateDoctorsForSurgery(surgeryID, addDoctorIDs, removeDoctorIDs);
        }

        if (updateRequest.getAddPrescriptionIDs() != null || updateRequest.getRemovePrescriptionIDs() != null) {
            List<Integer> addPrescriptionIDs = updateRequest.getAddPrescriptionIDs() != null ? updateRequest.getAddPrescriptionIDs() : List.of();
            List<Integer> removePrescriptionIDs = updateRequest.getRemovePrescriptionIDs() != null ? updateRequest.getRemovePrescriptionIDs() : List.of();
            surgeryDao.updatePrescriptionsForSurgery(surgeryID, addPrescriptionIDs, removePrescriptionIDs);
        }
    }
}
