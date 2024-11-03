package dev.praneeth.backend.Doctor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/{doctorID}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer doctorID) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorID); // Get the Optional<User>

        if (doctor.isPresent()) {
            return ResponseEntity.ok(doctor.get()); // Return the User if present
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if User not found
        }
    }

    @PostMapping("/login")
    public ResponseEntity<DoctorLoginResponse> loginDoctor(@RequestBody DoctorLoginRequest loginRequest) {
        Optional<DoctorLoginResponse> response = doctorService.validateLogin(loginRequest.getEmail(),
                loginRequest.getPassword());

        return response.map(res -> {
            res.setRole("doctor"); // Explicitly set the role as "Doctor"
            return ResponseEntity.ok(res);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutDoctor() {
        return ResponseEntity.ok("Successfully logged out. Please remove the token on the client side.");
    }

    @PostMapping
    public void addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
    }

    @DeleteMapping(path = "/{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Integer doctorId) {
        doctorService.deleteDoctor(doctorId);
    }

    @PutMapping(path = "/{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Integer doctorId,
            @RequestBody DoctorUpdateRequest updateRequest) {
        doctorService.updateDoctor(doctorId, updateRequest);
    }
}
