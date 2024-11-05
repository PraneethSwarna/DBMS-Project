package dev.praneeth.backend.Appointment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDao {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Update the RowMapper to handle null values
    private RowMapper<Appointment> appointmentRowMapper = new RowMapper<>() {
        @Override
        public Appointment mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("appointmentID"));

            Date date = rs.getDate("appointmentDate");
            appointment.setAppointmentDate(date != null ? date.toLocalDate() : null);

            Time time = rs.getTime("appointmentTime");
            appointment.setAppointmentTime(time != null ? time.toLocalTime() : null);

            appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
            appointment.setDiagnosisID(rs.getObject("diagnosisID") != null ? rs.getInt("diagnosisID") : null);
            appointment.setDoctorID(rs.getInt("doctorID"));
            appointment.setPatientID(rs.getInt("patientID"));
            return appointment;
        }
    };

    // Modified addAppointment method
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (appointmentDate, appointmentTime, status, diagnosisID, doctorID, patientID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                appointment.getAppointmentDate() != null ? Date.valueOf(appointment.getAppointmentDate()) : null,
                appointment.getAppointmentTime() != null ? Time.valueOf(appointment.getAppointmentTime()) : null,
                appointment.getStatus().name(),
                appointment.getDiagnosisID(),
                appointment.getDoctorID(),
                appointment.getPatientID());
    }

    // Fetch appointment by ID
    public Optional<Appointment> findById(Integer appointmentID) {
        String sql = "SELECT * FROM appointment WHERE appointmentID = ?";
        List<Appointment> appointments = jdbcTemplate.query(sql, appointmentRowMapper, appointmentID);
        return appointments.stream().findFirst();
    }

    // Fetch all appointments
    public List<Appointment> getAllAppointments() {
        String sql = "SELECT * FROM appointment";
        return jdbcTemplate.query(sql, appointmentRowMapper);
    }

    // Modified updateAppointment method
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointment SET appointmentDate = ?, appointmentTime = ?, status = ?, diagnosisID = ?, doctorID = ?, patientID = ? WHERE appointmentID = ?";
        jdbcTemplate.update(sql,
                appointment.getAppointmentDate() != null ? Date.valueOf(appointment.getAppointmentDate()) : null,
                appointment.getAppointmentTime() != null ? Time.valueOf(appointment.getAppointmentTime()) : null,
                appointment.getStatus().name(),
                appointment.getDiagnosisID(),
                appointment.getDoctorID(),
                appointment.getPatientID(),
                appointment.getAppointmentID());
    }

    // Delete an appointment by ID
    public void deleteAppointment(Integer appointmentID) {
        String sql = "DELETE FROM appointment WHERE appointmentID = ?";
        jdbcTemplate.update(sql, appointmentID);
    }

    // Get appointments by doctor ID
    public List<Appointment> getAppointmentsByDoctor(Integer doctorID) {
        String sql = "SELECT * FROM appointment WHERE doctorID = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorID);
    }

    // Get appointments by patient ID
    public List<Appointment> getAppointmentsByPatient(Integer patientID) {
        String sql = "SELECT * FROM appointment WHERE patientID = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, patientID);
    }
}
