package dev.praneeth.backend.Nurse;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{nurseID}")
    public ResponseEntity<Nurse> getNurseById(@PathVariable Integer nurseID) {
        Optional<Nurse> nurse = nurseService.getNurseById(nurseID);

        if (nurse.isPresent()) {
            return ResponseEntity.ok(nurse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<NurseLoginResponse> loginNurse(@RequestBody NurseLoginRequest loginRequest) {
        Optional<NurseLoginResponse> response = nurseService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

        return response.map(res -> {
            res.setRole("nurse"); // Explicitly set the role as "Nurse"
            return ResponseEntity.ok(res);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutNurse() {
        return ResponseEntity.ok("Successfully logged out. Please remove the token on the client side.");
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
