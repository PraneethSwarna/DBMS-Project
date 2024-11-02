package dev.praneeth.backend.LabTest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LabTestDao {

    private final JdbcTemplate jdbcTemplate;

    public LabTestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<LabTest> labTestRowMapper() {
        return new RowMapper<>() {
            @Override
            public LabTest mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                LabTest labTest = new LabTest();
                labTest.setLabTestID(rs.getInt("labTestID"));
                labTest.setName(rs.getString("name"));
                labTest.setDescription(rs.getString("description"));
                labTest.setNormalRange(rs.getString("normalRange"));
                labTest.setUnits(rs.getString("units"));
                return labTest;
            }
        };
    }

    public List<LabTest> getAllLabTests() {
        String sql = "SELECT * FROM lab_test";
        return jdbcTemplate.query(sql, labTestRowMapper());
    }

    public Optional<LabTest> getLabTestById(Integer id) {
        String sql = "SELECT * FROM lab_test WHERE labTestID = ?";
        return jdbcTemplate.query(sql, labTestRowMapper(), id).stream().findFirst();
    }

    public int addLabTest(LabTest labTest) {
        String sql = "INSERT INTO lab_test (name, description, normalRange, units) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                labTest.getName(),
                labTest.getDescription(),
                labTest.getNormalRange(),
                labTest.getUnits());
    }

    public int deleteLabTest(Integer id) {
        String sql = "DELETE FROM lab_test WHERE labTestID = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateLabTest(LabTest labTest) {
        String sql = "UPDATE lab_test SET name = ?, description = ?, normalRange = ?, units = ? WHERE labTestID = ?";
        return jdbcTemplate.update(sql,
                labTest.getName(),
                labTest.getDescription(),
                labTest.getNormalRange(),
                labTest.getUnits(),
                labTest.getLabTestID());
    }
}
