package dev.praneeth.backend.Surgery;

import java.util.List;
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
