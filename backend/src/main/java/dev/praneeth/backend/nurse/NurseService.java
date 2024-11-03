package dev.praneeth.backend.Nurse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    private final NurseDao nurseDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final NurseJwtService jwtService;

    public NurseService(NurseDao nurseDao, BCryptPasswordEncoder passwordEncoder, NurseJwtService jwtService) {
        this.nurseDao = nurseDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Get all nurses
    public List<Nurse> getNurses() {
        return nurseDao.getAllNurses();
    }

    // Get nurse by ID
    public Optional<Nurse> getNurseById(Integer nurseID) {
        return nurseDao.getNurseById(nurseID);
    }

    // Add a new nurse
    public void addNurse(Nurse nurse) {
        // Check if email is already in use
        Optional<Nurse> existingNurseByEmail = nurseDao.getNurseByEmail(nurse.getEmail());
        if (existingNurseByEmail.isPresent()) {
            throw new IllegalStateException("Email " + nurse.getEmail() + " is already in use");
        }

        nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
        nurseDao.addNurse(nurse);
    }

    // Delete a nurse by ID
    public void deleteNurse(Integer nurseID) {
        Optional<Nurse> nurse = nurseDao.getNurseById(nurseID);
        if (nurse.isEmpty()) {
            throw new IllegalStateException("Nurse with ID " + nurseID + " does not exist");
        }
        nurseDao.deleteNurse(nurseID);
    }

    // Update an existing nurse
    @Transactional
    public void updateNurse(Integer nurseID, NurseUpdateRequest updateRequest) {
        Optional<Nurse> existingNurse = nurseDao.getNurseById(nurseID);
        if (existingNurse.isEmpty()) {
            throw new IllegalStateException("Nurse with ID " + nurseID + " does not exist");
        }

        Nurse nurse = existingNurse.get();

        // Check for email uniqueness if the email is being updated
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            Optional<Nurse> nurseWithSameEmail = nurseDao.getNurseByEmail(updateRequest.getEmail());
            if (nurseWithSameEmail.isPresent() && !nurseWithSameEmail.get().getNurseID().equals(nurseID)) {
                throw new IllegalStateException("Email " + updateRequest.getEmail() + " is already in use");
            }
            nurse.setEmail(updateRequest.getEmail());
        }

        // Update other fields if present in request
        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isEmpty()) {
            nurse.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isEmpty()) {
            nurse.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getPhoneNumber() != null && !updateRequest.getPhoneNumber().isEmpty()) {
            nurse.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getShift() != null) {
            nurse.setShift(updateRequest.getShift());
        }
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().trim().isEmpty()) {
            nurse.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        nurseDao.updateNurse(nurseID, nurse);
    }

    // Updated validateLogin method to return NurseLoginResponse with token and role
    public Optional<NurseLoginResponse> validateLogin(String email, String rawPassword) {
        Optional<Nurse> nurse = nurseDao.getNurseByEmail(email);
        if (nurse.isPresent()) {
            System.out.println("Nurse found: " + nurse.get().getEmail());
            System.out.println("Password matches: " + passwordEncoder.matches(rawPassword, nurse.get().getPassword()));
            if (passwordEncoder.matches(rawPassword, nurse.get().getPassword())) {
                String token = jwtService.generateToken(nurse.get());
                String role = "nurse";
                return Optional.of(new NurseLoginResponse(
                        nurse.get().getNurseID(),
                        nurse.get().getFirstName(),
                        nurse.get().getLastName(),
                        nurse.get().getEmail(),
                        token,
                        role));
            }
        } else {
            System.out.println("Nurse not found with email: " + email);
        }
        return Optional.empty();
    }
    
}
