package dev.praneeth.backend.Doctor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    public DoctorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Doctor object
    private RowMapper<Doctor> doctorRowMapper = new RowMapper<>() {
        @Override
        public Doctor mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Doctor doctor = new Doctor();
            doctor.setDoctorID(rs.getInt("doctorID"));
            doctor.setFirstName(rs.getString("firstName"));
            doctor.setLastName(rs.getString("lastName"));
            doctor.setSpecialty(rs.getString("specialty"));
            doctor.setPhoneNumber(rs.getString("phoneNumber"));
            doctor.setEmail(rs.getString("email"));
            doctor.setOfficeNumber(rs.getString("officeNumber"));
            doctor.setPassword(rs.getString("password"));
            return doctor;
        }
    };

    // Fetch all doctors
    public List<Doctor> getAllDoctors() {
        String sql = "SELECT * FROM doctor";
        return jdbcTemplate.query(sql, doctorRowMapper);
    }

    // Fetch doctor by ID
    public Optional<Doctor> getDoctorById(Integer doctorID) {
        String sql = "SELECT * FROM doctor WHERE doctorID = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, doctorRowMapper, doctorID);
        return doctors.stream().findFirst(); // Return as Optional
    }

    // In DoctorDao
    public Optional<Doctor> getDoctorByEmail(String email) {
        String sql = "SELECT * FROM doctor WHERE email = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, doctorRowMapper, email);
        return doctors.stream().findFirst();
    }

    // Add a new doctor
    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (firstName, lastName, specialty, phoneNumber, email, officeNumber, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getOfficeNumber(),
                doctor.getPassword());
    }

    // Delete a doctor by ID
    public void deleteDoctor(Integer doctorID) {
        String sql = "DELETE FROM doctor WHERE doctorID = ?";
        jdbcTemplate.update(sql, doctorID);
    }

    // Update a doctor by ID
    public void updateDoctor(Integer doctorID, Doctor doctor) {
        String sql = "UPDATE doctor SET firstName = ?, lastName = ?, specialty = ?, phoneNumber = ?, email = ?, officeNumber = ?, password = ? WHERE doctorID = ?";
        jdbcTemplate.update(sql,
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getOfficeNumber(),
                doctor.getPassword(),
                doctorID);
    }
}
