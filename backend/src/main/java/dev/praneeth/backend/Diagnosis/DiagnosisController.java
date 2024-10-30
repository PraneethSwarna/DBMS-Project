package dev.praneeth.backend.Diagnosis;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    // Get all diagnoses
    @GetMapping
    public List<Diagnosis> getDiagnoses() {
        return diagnosisService.getDiagnoses();
    }

    // Add a new diagnosis
    @PostMapping
    public void addDiagnosis(@RequestBody Diagnosis diagnosis) {
        diagnosisService.addDiagnosis(diagnosis);
    }

    // Delete a diagnosis by ID
    @DeleteMapping(path = "/{diagnosisId}")
    public void deleteDiagnosis(@PathVariable("diagnosisId") Integer diagnosisId) {
        diagnosisService.deleteDiagnosis(diagnosisId);
    }

    // Update an existing diagnosis by ID
    @PutMapping(path = "/{diagnosisId}")
    public void updateDiagnosis(@PathVariable("diagnosisId") Integer diagnosisId, @RequestBody DiagnosisUpdateRequest updateRequest) {
        diagnosisService.updateDiagnosis(diagnosisId, updateRequest);
    }
}