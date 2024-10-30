package dev.praneeth.backend.Diagnosis;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DiagnosisDao {

    private final JdbcTemplate jdbcTemplate;

    public DiagnosisDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Diagnosis object
    private RowMapper<Diagnosis> diagnosisRowMapper = new RowMapper<>() {
        @Override
        public Diagnosis mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException { // Add @NonNull annotation
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setDiagnosisID(rs.getInt("diagnosisID"));
            diagnosis.setPrescriptionID(rs.getInt("prescriptionID"));
            diagnosis.setLabTestID(rs.getInt("labTestID"));
            diagnosis.setLabResultID(rs.getInt("labResultID"));
            diagnosis.setNotes(rs.getString("notes"));
            return diagnosis;
        }
    };

    // Fetch all diagnoses
    public List<Diagnosis> getAllDiagnoses() {
        String sql = "SELECT * FROM diagnosis";
        return jdbcTemplate.query(sql, diagnosisRowMapper);
    }

    // Fetch diagnosis by ID
    public Optional<Diagnosis> getDiagnosisById(Integer diagnosisID) {
        String sql = "SELECT * FROM diagnosis WHERE diagnosisID = ?";
        List<Diagnosis> diagnoses = jdbcTemplate.query(sql, diagnosisRowMapper, diagnosisID);
        return diagnoses.stream().findFirst(); // Return as Optional
    }

    // Add a new diagnosis
    public void addDiagnosis(Diagnosis diagnosis) {
        System.out.println("Prescription ID: " + diagnosis.getPrescriptionID()); // Debug statement
        System.out.println("Lab Test ID: " + diagnosis.getLabTestID());
        System.out.println("Lab Result ID: " + diagnosis.getLabResultID());

        String sql = "INSERT INTO diagnosis (prescriptionID, labTestID, labResultID, notes) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                diagnosis.getPrescriptionID(),
                diagnosis.getLabTestID(),
                diagnosis.getLabResultID(),
                diagnosis.getNotes());

    }

    // Delete a diagnosis by ID
    public void deleteDiagnosis(Integer diagnosisID) {
        String sql = "DELETE FROM diagnosis WHERE diagnosisID = ?";
        jdbcTemplate.update(sql, diagnosisID);
    }

    // Update a diagnosis by ID
    public void updateDiagnosis(Integer diagnosisID, Diagnosis diagnosis) {
        String sql = "UPDATE diagnosis SET prescriptionID = ?, labTestID = ?, labResultID = ?, notes = ? WHERE diagnosisID = ?";
        jdbcTemplate.update(sql,
                diagnosis.getPrescriptionID(),
                diagnosis.getLabTestID(),
                diagnosis.getLabResultID(),
                diagnosis.getNotes(),
                diagnosisID);
    }
}