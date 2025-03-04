package dev.praneeth.backend.HomeConsultation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/home_consultation")
public class HomeConsultationController {

    private final HomeConsultationService homeConsultationService;

    public HomeConsultationController(HomeConsultationService homeConsultationService) {
        this.homeConsultationService = homeConsultationService;
    }

    // Retrieve all consultations
    @GetMapping
    public List<HomeConsultation> getConsultations() {
        return homeConsultationService.getConsultations();
    }

    // Retrieve consultations by patient ID
    @GetMapping(path = "/patient/{patientId}")
    public List<HomeConsultation> getHomeConsultationsByPatient(@PathVariable("patientId") Integer patientId) {
        return homeConsultationService.getHomeConsultationsByPatient(patientId);
    }

    // Retrieve consultations by doctor ID
    @GetMapping(path = "/doctor/{doctorId}")
    public List<HomeConsultation> getHomeConsultationsByDoctor(@PathVariable("doctorId") Integer doctorId) {
        return homeConsultationService.getHomeConsultationsByDoctor(doctorId);
    }

    // Add a new consultation
    @PostMapping
    public void addConsultation(@RequestBody HomeConsultation consultation) {
        homeConsultationService.addConsultation(consultation);
    }

    // Delete a consultation by ID
    @DeleteMapping(path = "/{consultationID}")
    public void deleteConsultation(@PathVariable("consultationID") Integer consultationID) {
        homeConsultationService.deleteConsultation(consultationID);
    }

    // Update a consultation by ID
    @PutMapping(path = "/{consultationID}")
    public void updateConsultation(@PathVariable("consultationID") Integer consultationID, @RequestBody HomeConsultationUpdateRequest updatedConsultation) {
        homeConsultationService.updateConsultation(consultationID, updatedConsultation);
    }
}
