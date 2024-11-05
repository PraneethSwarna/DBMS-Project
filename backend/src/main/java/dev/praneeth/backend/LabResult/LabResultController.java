package dev.praneeth.backend.LabResult;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/lab_result")
public class LabResultController {

    private final LabResultService labResultService;

    public LabResultController(LabResultService labResultService) {
        this.labResultService = labResultService;
    }

    @GetMapping
    public List<LabResult> getAllLabResults() {
        return labResultService.getAllLabResults();
    }

    @GetMapping(path = "/{labResultId}")
    public Optional<LabResult> getLabResultById(@PathVariable("labResultId") Integer labResultId) {
        return labResultService.getLabResultById(labResultId);
    }

    @PostMapping
    public void addLabResult(@RequestBody LabResult labResult) {
        labResultService.addLabResult(labResult);
    }

    @DeleteMapping(path = "/{labResultId}")
    public void deleteLabResult(@PathVariable("labResultId") Integer labResultId) {
        labResultService.deleteLabResult(labResultId);
    }

    @PutMapping(path = "/{labResultId}")
    public void updateLabResult(@PathVariable("labResultId") Integer labResultId, @RequestBody LabResultUpdateRequest updateRequest) {
        labResultService.updateLabResult(labResultId, updateRequest);
    }
}
