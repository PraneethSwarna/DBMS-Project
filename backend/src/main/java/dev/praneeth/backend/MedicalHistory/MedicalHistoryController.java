package dev.praneeth.backend.MedicalHistory;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/medical_history")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @GetMapping
    public List<MedicalHistory> getMedicalHistories() {
        return medicalHistoryService.getMedicalHistories();
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalHistory> getMedicalHistoriesByPatient(@PathVariable Integer patientId) {
        return medicalHistoryService.getHistoriesByPatient(patientId);
    }

    @PostMapping
    public void addMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        medicalHistoryService.addMedicalHistory(medicalHistory);
    }

    @DeleteMapping(path = "/{historyId}")
    public void deleteMedicalHistory(@PathVariable("historyId") Integer historyId) {
        medicalHistoryService.deleteMedicalHistory(historyId);
    }

    @PutMapping(path = "/{historyId}")
    public void updateMedicalHistory(@PathVariable("historyId") Integer historyId,
            @RequestBody MedicalHistoryUpdateRequest updateRequest) {
        medicalHistoryService.updateMedicalHistory(historyId, updateRequest);
    }
}
