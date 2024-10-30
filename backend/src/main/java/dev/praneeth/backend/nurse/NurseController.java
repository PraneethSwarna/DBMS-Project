package dev.praneeth.backend.Nurse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/nurse")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping
    public List<Nurse> getNurses() {
        return nurseService.getNurses();
    }

    @PostMapping
    public void addNurse(@RequestBody Nurse nurse) {
        nurseService.addNurse(nurse);
    }

    @DeleteMapping(path = "/{nurseId}")
    public void deleteNurse(@PathVariable("nurseId") Integer nurseId) {
        nurseService.deleteNurse(nurseId);
    }

    @PutMapping(path = "/{nurseId}")
    public void updateNurse(@PathVariable("nurseId") Integer nurseId, @RequestBody NurseUpdateRequest updateRequest) {
        nurseService.updateNurse(nurseId, updateRequest);
    }
}
