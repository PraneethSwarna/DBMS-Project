package dev.praneeth.backend.Prescription;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping
    public List<Prescription> getPrescriptions() {
        return prescriptionService.getPrescriptions();
    }

    @GetMapping(path = "/{prescriptionId}")
    public Optional<Prescription> getPrescriptionById(@PathVariable("prescriptionId") Integer prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId);
    }

    @PostMapping
    public void addPrescription(@RequestBody Prescription prescription) {
        prescriptionService.addPrescription(prescription);
    }

    @DeleteMapping(path = "/{prescriptionId}")
    public void deletePrescription(@PathVariable("prescriptionId") Integer prescriptionId) {
        prescriptionService.deletePrescription(prescriptionId);
    }

    @PutMapping(path = "/{prescriptionId}")
    public void updatePrescription(@PathVariable("prescriptionId") Integer prescriptionId, @RequestBody PrescriptionUpdateRequest updateRequest) {
        prescriptionService.updatePrescription(prescriptionId, updateRequest);
    }
}
