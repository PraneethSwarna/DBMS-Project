package dev.praneeth.backend.Hospitalization;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class HospitalizationDao {

    private final JdbcTemplate jdbcTemplate;

    public HospitalizationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Hospitalization object
    private RowMapper<Hospitalization> hospitalizationRowMapper() {
        return new RowMapper<>() {
            @Override
            public Hospitalization mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
                Hospitalization hospitalization = new Hospitalization();
                hospitalization.setHospitalizationID(rs.getInt("hospitalizationID"));
                hospitalization.setAdmissionDate(rs.getDate("admissionDate").toLocalDate());
                hospitalization.setDischargeDate(rs.getDate("dischargeDate") != null ? rs.getDate("dischargeDate").toLocalDate() : null);
                hospitalization.setReason(rs.getString("reason"));
                hospitalization.setNotes(rs.getString("notes"));
                hospitalization.setPatientID(rs.getInt("patientID"));
                hospitalization.setRoomNumber(rs.getString("roomNumber"));
                return hospitalization;
            }
        };
    }

    // Retrieve all hospitalizations
    public List<Hospitalization> getAllHospitalizations() {
        String sql = "SELECT * FROM hospitalization";
        return jdbcTemplate.query(sql, hospitalizationRowMapper());
    }

    // Retrieve a hospitalization by ID
    public Optional<Hospitalization> getHospitalizationById(Integer id) {
        String sql = "SELECT * FROM hospitalization WHERE hospitalizationID = ?";
        return jdbcTemplate.query(sql, hospitalizationRowMapper(), id).stream().findFirst();
    }

    // Retrieve hospitalizations by patient ID
    public List<Hospitalization> getHospitalizationByPatient(Integer patientID) {
        String sql = "SELECT * FROM hospitalization WHERE patientID = ?";
        return jdbcTemplate.query(sql, hospitalizationRowMapper(), patientID);
    }

    // Add a new hospitalization
    public void addHospitalization(Hospitalization hospitalization) {
        String sql = "INSERT INTO hospitalization (admissionDate, dischargeDate, reason, notes, patientID, roomNumber) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                hospitalization.getAdmissionDate(),
                hospitalization.getDischargeDate() != null ? hospitalization.getDischargeDate() : null,
                hospitalization.getReason(),
                hospitalization.getNotes(),
                hospitalization.getPatientID(),
                hospitalization.getRoomNumber());
    }

    // Update a hospitalization
    public void updateHospitalization(Hospitalization hospitalization) {
        String sql = "UPDATE hospitalization SET admissionDate = ?, dischargeDate = ?, reason = ?, notes = ?, patientID = ?, roomNumber = ? WHERE hospitalizationID = ?";
        jdbcTemplate.update(sql,
                hospitalization.getAdmissionDate(),
                hospitalization.getDischargeDate() != null ? hospitalization.getDischargeDate() : null,
                hospitalization.getReason(),
                hospitalization.getNotes(),
                hospitalization.getPatientID(),
                hospitalization.getRoomNumber(),
                hospitalization.getHospitalizationID());
    }

    // Delete a hospitalization by ID
    public void deleteHospitalization(Integer id) {
        String sql = "DELETE FROM hospitalization WHERE hospitalizationID = ?";
        jdbcTemplate.update(sql, id);
    }

    
}
