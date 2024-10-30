package dev.praneeth.backend.Doctor;

import java.util.List;
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

    @PostMapping
    public void addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
    }

    @DeleteMapping(path = "/{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Integer doctorId) {
        doctorService.deleteDoctor(doctorId);
    }

    @PutMapping(path = "/{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Integer doctorId, @RequestBody DoctorUpdateRequest updateRequest) {
        doctorService.updateDoctor(doctorId, updateRequest);
    }
}
