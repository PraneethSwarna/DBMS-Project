package dev.praneeth.backend.Appointment;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final AppointmentDao appointmentDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public List<Appointment> getAppointments() {
        return appointmentDao.getAllAppointments();
    }

    public void addAppointment(Appointment appointment) {
        appointmentDao.addAppointment(appointment);
    }

    public void deleteAppointment(Integer appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentDao.findById(appointmentId);
        if (!appointmentOptional.isPresent()) {
            throw new IllegalStateException("Appointment with id " + appointmentId + " does not exist");
        }
        appointmentDao.deleteAppointment(appointmentId);
    }

    @Transactional
    public void updateAppointment(Integer appointmentId, AppointmentUpdateRequest updateRequest) {
        Optional<Appointment> appointmentOptional = appointmentDao.findById(appointmentId);

        if (!appointmentOptional.isPresent()) {
            throw new IllegalStateException("Appointment with id " + appointmentId + " does not exist");
        }

        Appointment appointment = appointmentOptional.get();

        // Update fields only if they are present in the request
        if (updateRequest.getAppointmentDate() != null) {
            appointment.setAppointmentDate(updateRequest.getAppointmentDate());
        }
        if (updateRequest.getAppointmentTime() != null) {
            appointment.setAppointmentTime(updateRequest.getAppointmentTime());
        }
        if (updateRequest.getStatus() != null) {
            // Convert String to Appointment.Status enum
            Appointment.Status status = Appointment.Status.valueOf(updateRequest.getStatus());
            appointment.setStatus(status);
        }

        // Update in the DAO
        appointmentDao.updateAppointment(appointment); // Assuming this method takes an Appointment object.
    }

    public List<Appointment> getAppointmentsByDoctor(Integer doctorId) {
        return appointmentDao.getAppointmentsByDoctor(doctorId);
    }

    public List<Appointment> getAppointmentsByPatient(Integer patientId) {
        return appointmentDao.getAppointmentsByPatient(patientId);
    }
}
