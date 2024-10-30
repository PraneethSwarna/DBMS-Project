package dev.praneeth.backend.Nurse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    private final NurseDao nurseDao;

    public NurseService(NurseDao nurseDao) {
        this.nurseDao = nurseDao;
    }

    // Get all nurses
    public List<Nurse> getNurses() {
        return nurseDao.getAllNurses();
    }

    // Add a new nurse with unique email validation
    public void addNurse(Nurse nurse) {
        // Check if the email is already in use
        Optional<Nurse> existingNurseByEmail = nurseDao.getNurseByEmail(nurse.getEmail());
        if (existingNurseByEmail.isPresent()) {
            throw new IllegalStateException("Email " + nurse.getEmail() + " is already in use by another nurse");
        }
        nurseDao.addNurse(nurse);
    }

    // Delete a nurse by ID with validation
    public void deleteNurse(Integer nurseId) {
        Optional<Nurse> nurseOptional = nurseDao.getNurseById(nurseId);
        if (nurseOptional.isEmpty()) {
            throw new IllegalStateException("Nurse with ID " + nurseId + " does not exist");
        }
        nurseDao.deleteNurse(nurseId);
    }

    // Update an existing nurse with validations for unique email
    @Transactional
    public void updateNurse(Integer nurseId, NurseUpdateRequest updateRequest) {
        Optional<Nurse> existingNurseOptional = nurseDao.getNurseById(nurseId);
        if (existingNurseOptional.isEmpty()) {
            throw new IllegalStateException("Nurse with ID " + nurseId + " does not exist");
        }

        Nurse nurse = existingNurseOptional.get();

        // Validate unique email if updating email field
        if (updateRequest.getEmail() != null) {
            Optional<Nurse> nurseWithSameEmail = nurseDao.getNurseByEmail(updateRequest.getEmail());
            if (nurseWithSameEmail.isPresent() && !nurseWithSameEmail.get().getNurseID().equals(nurseId)) {
                throw new IllegalStateException("Email " + updateRequest.getEmail() + " is already in use by another nurse");
            }
            nurse.setEmail(updateRequest.getEmail());
        }

        // Update fields if present in request
        if (updateRequest.getFirstName() != null) {
            nurse.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            nurse.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getPhoneNumber() != null) {
            nurse.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getShift() != null) {
            nurse.setShift(updateRequest.getShift());
        }

        nurseDao.updateNurse(nurseId, nurse);
    }
}
