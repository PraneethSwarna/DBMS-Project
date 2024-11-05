package dev.praneeth.backend.Surgery;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/surgery")
public class SurgeryController {

    private final SurgeryService surgeryService;

    public SurgeryController(SurgeryService surgeryService) {
        this.surgeryService = surgeryService;
    }

    @GetMapping
    public List<Surgery> getSurgeries() {
        return surgeryService.getSurgeries();
    }

    @GetMapping(path = "/{surgeryId}")
    public Optional<Surgery> getSurgeryById(@PathVariable("surgeryId") Integer surgeryId) {
        return surgeryService.getSurgeryById(surgeryId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<Surgery> getSurgeriesByPatient(@PathVariable("patientId") Integer patientId) {
        return surgeryService.getSurgeriesByPatient(patientId);
    }

    @GetMapping(path = "/doctor/{doctorId}")
    public List<Surgery> getSurgeriesByDoctor(@PathVariable("doctorId") Integer doctorId) {
        return surgeryService.getSurgeriesByDoctor(doctorId);
    }

    @PostMapping
    public void addSurgery(@RequestBody Surgery surgery) {
        surgeryService.addSurgery(surgery);
    }

    @DeleteMapping(path = "/{surgeryId}")
    public void deleteSurgery(@PathVariable("surgeryId") Integer surgeryId) {
        surgeryService.deleteSurgery(surgeryId);
    }

    @PutMapping(path = "/{surgeryId}")
    public void updateSurgery(@PathVariable("surgeryId") Integer surgeryId, @RequestBody SurgeryUpdateRequest updateRequest) {
        surgeryService.updateSurgery(surgeryId, updateRequest);
    }
}
