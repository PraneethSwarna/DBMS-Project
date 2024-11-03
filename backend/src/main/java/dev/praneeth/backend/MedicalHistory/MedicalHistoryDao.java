package dev.praneeth.backend.MedicalHistory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicalHistoryDao {

    private final JdbcTemplate jdbcTemplate;

    public MedicalHistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for MedicalHistory
    @SuppressWarnings("unused")
    private RowMapper<MedicalHistory> medicalHistoryRowMapper = (rs, rowNum) -> new MedicalHistory(
        rs.getInt("historyID"),
        rs.getObject("recordDate", LocalDate.class),
        rs.getString("diagnosis"),
        rs.getString("treatment"),
        rs.getString("notes"),
        rs.getInt("patientID")
    );

    public List<MedicalHistory> getAllMedicalHistories() {
        String sql = "SELECT * FROM medical_history";
        return jdbcTemplate.query(sql, medicalHistoryRowMapper);
    }

    public void addMedicalHistory(MedicalHistory medicalHistory) {
        String sql = "INSERT INTO medical_history (recordDate, diagnosis, treatment, notes, patientID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, medicalHistory.getRecordDate(), medicalHistory.getDiagnosis(), medicalHistory.getTreatment(), medicalHistory.getNotes(), medicalHistory.getPatientID());
    }

    public Optional<MedicalHistory> getMedicalHistoryById(Integer historyId) {
        String sql = "SELECT * FROM medical_history WHERE historyID = ?";
        List<MedicalHistory> results = jdbcTemplate.query(sql, medicalHistoryRowMapper, historyId);
        return results.stream().findFirst();
    }

    public void deleteMedicalHistory(Integer historyId) {
        String sql = "DELETE FROM medical_history WHERE historyID = ?";
        jdbcTemplate.update(sql, historyId);
    }

    public void updateMedicalHistory(MedicalHistory medicalHistory) {
        String sql = "UPDATE medical_history SET recordDate = ?, diagnosis = ?, treatment = ?, notes = ?, patientID = ? WHERE historyID = ?";
        jdbcTemplate.update(sql, medicalHistory.getRecordDate(), medicalHistory.getDiagnosis(), medicalHistory.getTreatment(), medicalHistory.getNotes(), medicalHistory.getPatientID(), medicalHistory.getHistoryID());
    }

    public List<MedicalHistory> getHistoriesByPatientId(Integer patientId) {
        String sql = "SELECT * FROM medical_history WHERE patientID = ?";
        return jdbcTemplate.query(sql, medicalHistoryRowMapper, patientId);
    }
}
