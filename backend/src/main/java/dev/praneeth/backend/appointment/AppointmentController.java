package dev.praneeth.backend.Appointment;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAppointments() {
        return appointmentService.getAppointments();
    }

    @PostMapping
    public void addAppointment(@RequestBody Appointment appointment) {
        appointmentService.addAppointment(appointment);
    }

    @DeleteMapping(path = "/{appointmentId}")
    public void deleteAppointment(@PathVariable("appointmentId") Integer appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }

    @PutMapping(path = "/{appointmentId}")
    public void updateAppointment(@PathVariable("appointmentId") Integer appointmentId, @RequestBody AppointmentUpdateRequest updateRequest) {
        appointmentService.updateAppointment(appointmentId, updateRequest);
    }
}
