package dev.praneeth.backend.HomeConsultation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class HomeConsultationDao {

    private final JdbcTemplate jdbcTemplate;

    public HomeConsultationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for HomeConsultation object
    private RowMapper<HomeConsultation> consultationRowMapper() {
        return new RowMapper<>() {
            @Override
            public HomeConsultation mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
                HomeConsultation consultation = new HomeConsultation();
                consultation.setHomeConsultationID(rs.getInt("homeConsultationID"));
                consultation.setConsultationDate(rs.getDate("consultationDate").toLocalDate());
                consultation.setConsultationTime(rs.getTime("consultationTime").toLocalTime());
                consultation.setDoctorID(rs.getObject("doctorID") != null ? rs.getInt("doctorID") : null);
                consultation.setNotes(rs.getString("notes"));
                consultation.setOutcome(rs.getString("outcome"));
                consultation.setPatientID(rs.getInt("patientID"));
                return consultation;
            }
        };
    }

    // Retrieve all consultations
    public List<HomeConsultation> getAllConsultations() {
        String sql = "SELECT * FROM home_consultation";
        return jdbcTemplate.query(sql, consultationRowMapper());
    }

    // Retrieve consultations by patient ID
    public List<HomeConsultation> getHomeConsultationsByPatient(Integer patientID) {
        String sql = "SELECT * FROM home_consultation WHERE patientID = ?";
        return jdbcTemplate.query(sql, consultationRowMapper(), patientID);
    }

    // Retrieve consultations by doctor ID
    public List<HomeConsultation> getHomeConsultationsByDoctor(Integer doctorID) {
        String sql = "SELECT * FROM home_consultation WHERE doctorID = ?";
        return jdbcTemplate.query(sql, consultationRowMapper(), doctorID);
    }

    // Retrieve a consultation by ID
    public Optional<HomeConsultation> getConsultationById(Integer id) {
        String sql = "SELECT * FROM home_consultation WHERE homeConsultationID = ?";
        return jdbcTemplate.query(sql, consultationRowMapper(), id).stream().findFirst();
    }

    // Add a new consultation
    public void addConsultation(HomeConsultation consultation) {
        String sql = "INSERT INTO home_consultation (consultationDate, consultationTime, outcome, doctorID, patientID, notes) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                consultation.getConsultationDate(),
                consultation.getConsultationTime(),
                consultation.getOutcome(),
                consultation.getDoctorID(),
                consultation.getPatientID(),
                consultation.getNotes());
    }

    // Delete a consultation by ID
    public int deleteConsultation(Integer id) {
        String sql = "DELETE FROM home_consultation WHERE homeConsultationID = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Update a consultation
    public void updateConsultation(HomeConsultation consultation) {
        String sql = "UPDATE home_consultation SET consultationDate = ?, consultationTime = ?, outcome = ?, doctorID = ?, patientID = ?, notes = ? WHERE homeConsultationID = ?";
        jdbcTemplate.update(sql,
                consultation.getConsultationDate(),
                consultation.getConsultationTime(),
                consultation.getOutcome(),
                consultation.getDoctorID(),
                consultation.getPatientID(),
                consultation.getNotes(),
                consultation.getHomeConsultationID());
    }
}
