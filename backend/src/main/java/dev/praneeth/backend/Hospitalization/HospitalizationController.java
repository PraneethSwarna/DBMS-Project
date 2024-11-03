package dev.praneeth.backend.Hospitalization;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/hospitalization")
public class HospitalizationController {

    private HospitalizationService hospitalizationService;

    public HospitalizationController(HospitalizationService hospitalizationService) {
        this.hospitalizationService = hospitalizationService;
    }

    @GetMapping
    public List<Hospitalization> getHospitalizations() {
        return hospitalizationService.getHospitalizations();
    }

    @PostMapping
    public void addHospitalization(@RequestBody Hospitalization hospitalization) {
        hospitalizationService.addHospitalization(hospitalization);
    }

    @DeleteMapping(path = "/{hospitalizationID}")
    public void deleteHospitalization(@PathVariable("hospitalizationID") Integer hospitalizationID) {
        hospitalizationService.deleteHospitalization(hospitalizationID);
    }

    @PutMapping(path = "/{hospitalizationID}")
    public void updateHospitalization(@PathVariable("hospitalizationID") Integer hospitalizationID, @RequestBody HospitalizationUpdateRequest hospitalization) {
        hospitalizationService.updateHospitalization(hospitalizationID, hospitalization);
    }
}
