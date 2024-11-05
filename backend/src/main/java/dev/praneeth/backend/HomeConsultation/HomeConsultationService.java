package dev.praneeth.backend.HomeConsultation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import java.util.Optional;

@Service
public class HomeConsultationService {

    private final HomeConsultationDao homeConsultationDao;

    public HomeConsultationService(HomeConsultationDao homeConsultationDao) {
        this.homeConsultationDao = homeConsultationDao;
    }

    // Retrieve all consultations
    public List<HomeConsultation> getConsultations() {
        return homeConsultationDao.getAllConsultations();
    }

    // Retrieve consultations by patient ID
    public List<HomeConsultation> getHomeConsultationsByPatient(Integer patientID) {
        return homeConsultationDao.getHomeConsultationsByPatient(patientID);
    }

    // Retrieve consultations by doctor ID
    public List<HomeConsultation> getHomeConsultationsByDoctor(Integer doctorID) {
        return homeConsultationDao.getHomeConsultationsByDoctor(doctorID);
    }

    // Add a new consultation
    public void addConsultation(HomeConsultation consultation) {
        homeConsultationDao.addConsultation(consultation);
    }

    // Delete a consultation by ID
    public void deleteConsultation(Integer consultationID) {
        boolean exists = homeConsultationDao.getConsultationById(consultationID).isPresent();
        if (!exists) {
            throw new IllegalStateException("Consultation with ID " + consultationID + " does not exist.");
        }
        homeConsultationDao.deleteConsultation(consultationID);
    }

    // Update a consultation by ID
    @Transactional
    public void updateConsultation(Integer consultationID, HomeConsultationUpdateRequest updateRequest) {
        HomeConsultation consultation = homeConsultationDao.getConsultationById(consultationID)
                .orElseThrow(() -> new IllegalStateException("Consultation with ID " + consultationID + " does not exist."));

        // Update fields if provided
        if (updateRequest.getConsultationDate() != null) {
            consultation.setConsultationDate(updateRequest.getConsultationDate());
        }

        if (updateRequest.getConsultationTime() != null) {
            consultation.setConsultationTime(updateRequest.getConsultationTime());
        }

        if (updateRequest.getOutcome() != null && !updateRequest.getOutcome().trim().isEmpty()) {
            consultation.setOutcome(updateRequest.getOutcome());
        }

        if (updateRequest.getDoctorID() != null) {
            consultation.setDoctorID(updateRequest.getDoctorID());
        }

        if (updateRequest.getPatientID() != null) {
            consultation.setPatientID(updateRequest.getPatientID());
        }

        if (updateRequest.getNotes() != null) {
            consultation.setNotes(updateRequest.getNotes());
        }

        homeConsultationDao.updateConsultation(consultation);
    }
}
