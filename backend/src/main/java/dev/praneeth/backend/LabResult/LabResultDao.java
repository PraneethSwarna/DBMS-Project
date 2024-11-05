package dev.praneeth.backend.LabResult;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LabResultDao {

    private final JdbcTemplate jdbcTemplate;

    public LabResultDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unused")
    private RowMapper<LabResult> labResultRowMapper() {
        return (rs, rowNum) -> {
            LabResult labResult = new LabResult();
            labResult.setLabResultID(rs.getInt("labResultID"));
            labResult.setResultValue(rs.getDouble("resultValue"));
            labResult.setTestDate(rs.getDate("testDate").toLocalDate());
            labResult.setNotes(rs.getString("notes"));
            return labResult;
        };
    }

    public List<LabResult> getAllLabResults() {
        String sql = "SELECT * FROM lab_result";
        return jdbcTemplate.query(sql, labResultRowMapper());
    }

    public Optional<LabResult> getLabResultById(Integer id) {
        String sql = "SELECT * FROM lab_result WHERE labResultID = ?";
        return jdbcTemplate.query(sql, labResultRowMapper(), id).stream().findFirst();
    }

    public int addLabResult(LabResult labResult) {
        String sql = "INSERT INTO lab_result (resultValue, testDate, notes) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                labResult.getResultValue(),
                labResult.getTestDate(),
                labResult.getNotes());
    }

    public int deleteLabResult(Integer id) {
        String sql = "DELETE FROM lab_result WHERE labResultID = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateLabResult(LabResult labResult) {
        String sql = "UPDATE lab_result SET resultValue = ?, testDate = ?, notes = ? WHERE labResultID = ?";
        return jdbcTemplate.update(sql,
                labResult.getResultValue(),
                labResult.getTestDate(),
                labResult.getNotes(),
                labResult.getLabResultID());
    }
}
