package dev.praneeth.backend.surgery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SurgeryDao {

    private final JdbcTemplate jdbcTemplate;

    public SurgeryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Surgery object
    private RowMapper<Surgery> surgeryRowMapper = new RowMapper<>() {
        @Override
        public Surgery mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            Surgery surgery = new Surgery();
            surgery.setSurgeryID(rs.getInt("surgery_id"));
            surgery.setSurgeryDate(rs.getDate("surgery_date").toLocalDate());
            surgery.setSurgeryType(rs.getString("surgery_type"));
            surgery.setOutcome(rs.getString("outcome"));
            surgery.setPrescriptionID(rs.getInt("prescription_id"));
            surgery.setNotes(rs.getString("notes"));
            surgery.setDoctorID(rs.getInt("doctor_id"));
            return surgery;
        }
    };

    // Fetch all surgeries
    public List<Surgery> getAllSurgeries() {
        String sql = "SELECT * FROM surgery";
        return jdbcTemplate.query(sql, surgeryRowMapper);
    }

    // Fetch surgery by ID
    public Optional<Surgery> getSurgeryById(Integer surgeryID) {
        String sql = "SELECT * FROM surgery WHERE surgery_id = ?";
        List<Surgery> surgeries = jdbcTemplate.query(sql, surgeryRowMapper, surgeryID);
        return surgeries.stream().findFirst(); // Return as Optional
    }

    // Add a new surgery
    public void addSurgery(Surgery surgery) {
        String sql = "INSERT INTO surgery (surgery_date, surgery_type, outcome, prescription_id, notes, doctor_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                surgery.getSurgeryDate(),
                surgery.getSurgeryType(),
                surgery.getOutcome(),
                surgery.getPrescriptionID(),
                surgery.getNotes(),
                surgery.getDoctorID());
    }

    // Delete a surgery by ID
    public void deleteSurgery(Integer surgeryID) {
        String sql = "DELETE FROM surgery WHERE surgery_id = ?";
        jdbcTemplate.update(sql, surgeryID);
    }

    // Update a surgery by ID
    public void updateSurgery(Integer surgeryID, Surgery surgery) {
        String sql = "UPDATE surgery SET surgery_date = ?, surgery_type = ?, outcome = ?, prescription_id = ?, notes = ?, doctor_id = ? WHERE surgery_id = ?";
        jdbcTemplate.update(sql,
                surgery.getSurgeryDate(),
                surgery.getSurgeryType(),
                surgery.getOutcome(),
                surgery.getPrescriptionID(),
                surgery.getNotes(),
                surgery.getDoctorID(),
                surgeryID);
    }
}