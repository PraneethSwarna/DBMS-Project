package dev.praneeth.backend.Doctor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorDao doctorDao;

    public DoctorService(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    // Get all doctors
    public List<Doctor> getDoctors() {
        return doctorDao.getAllDoctors();
    }

    // Add a new doctor
    public void addDoctor(Doctor doctor) {
        // Check if email is already in use
        Optional<Doctor> existingDoctorByEmail = doctorDao.getDoctorByEmail(doctor.getEmail());
        if (existingDoctorByEmail.isPresent()) {
            throw new IllegalStateException("Email " + doctor.getEmail() + " is already in use");
        }

        // Additional validations can be added here, such as phone number uniqueness if required
        doctorDao.addDoctor(doctor);
    }

    // Delete a doctor by ID
    public void deleteDoctor(Integer doctorID) {
        Optional<Doctor> doctor = doctorDao.getDoctorById(doctorID);
        if (doctor.isEmpty()) {
            throw new IllegalStateException("Doctor with ID " + doctorID + " does not exist");
        }
        doctorDao.deleteDoctor(doctorID);
    }

    // Update an existing doctor
    @Transactional
    public void updateDoctor(Integer doctorID, DoctorUpdateRequest updateRequest) {
        Optional<Doctor> existingDoctor = doctorDao.getDoctorById(doctorID);
        if (existingDoctor.isEmpty()) {
            throw new IllegalStateException("Doctor with ID " + doctorID + " does not exist");
        }

        Doctor doctor = existingDoctor.get();

        // Check for email uniqueness if the email is being updated
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            Optional<Doctor> doctorWithSameEmail = doctorDao.getDoctorByEmail(updateRequest.getEmail());
            if (doctorWithSameEmail.isPresent() && !doctorWithSameEmail.get().getDoctorID().equals(doctorID)) {
                throw new IllegalStateException("Email " + updateRequest.getEmail() + " is already in use");
            }
            doctor.setEmail(updateRequest.getEmail());
        }

        // Update other fields if present in request
        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isEmpty()) {
            doctor.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isEmpty()) {
            doctor.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getSpecialty() != null && !updateRequest.getSpecialty().isEmpty()) {
            doctor.setSpecialty(updateRequest.getSpecialty());
        }
        if (updateRequest.getPhoneNumber() != null && !updateRequest.getPhoneNumber().isEmpty()) {
            doctor.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getOfficeNumber() != null && !updateRequest.getOfficeNumber().isEmpty()) {
            doctor.setOfficeNumber(updateRequest.getOfficeNumber());
        }

        doctorDao.updateDoctor(doctorID, doctor);
    }
}
