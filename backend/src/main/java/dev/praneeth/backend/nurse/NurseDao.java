package dev.praneeth.backend.Nurse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class NurseDao {

    private final JdbcTemplate jdbcTemplate;

    public NurseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map result set to Nurse object
    private final RowMapper<Nurse> nurseRowMapper = new RowMapper<>() {
        @Override
        public Nurse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Nurse nurse = new Nurse();
            nurse.setNurseID(rs.getInt("nurseID"));
            nurse.setFirstName(rs.getString("firstName"));
            nurse.setLastName(rs.getString("lastName"));
            nurse.setPhoneNumber(rs.getString("phoneNumber"));
            nurse.setEmail(rs.getString("email"));
            nurse.setShift(Nurse.Shift.valueOf(rs.getString("shift")));
            nurse.setPassword(rs.getString("password"));
            return nurse;
        }
    };

    // Fetch all nurses
    public List<Nurse> getAllNurses() {
        String sql = "SELECT * FROM nurse";
        return jdbcTemplate.query(sql, nurseRowMapper);
    }

    // Fetch nurse by ID
    public Optional<Nurse> getNurseById(Integer nurseID) {
        String sql = "SELECT * FROM nurse WHERE nurseID = ?";
        List<Nurse> nurses = jdbcTemplate.query(sql, nurseRowMapper, nurseID);
        return nurses.stream().findFirst();
    }

    // Fetch nurse by email
    public Optional<Nurse> getNurseByEmail(String email) {
        String sql = "SELECT * FROM nurse WHERE email = ?";
        List<Nurse> nurses = jdbcTemplate.query(sql, nurseRowMapper, email);
        return nurses.stream().findFirst();
    }

    // Add a new nurse
    public void addNurse(Nurse nurse) {
        String sql = "INSERT INTO nurse (firstName, lastName, phoneNumber, email, shift, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                nurse.getFirstName(),
                nurse.getLastName(),
                nurse.getPhoneNumber(),
                nurse.getEmail(),
                nurse.getShift().name(),
                nurse.getPassword());
    }

    // Delete a nurse by ID
    public void deleteNurse(Integer nurseID) {
        String sql = "DELETE FROM nurse WHERE nurseID = ?";
        jdbcTemplate.update(sql, nurseID);
    }

    // Update a nurse by ID
    public void updateNurse(Integer nurseID, Nurse nurse) {
        String sql = "UPDATE nurse SET firstName = ?, lastName = ?, phoneNumber = ?, email = ?, shift = ?, password = ? WHERE nurseID = ?";
        jdbcTemplate.update(sql,
                nurse.getFirstName(),
                nurse.getLastName(),
                nurse.getPhoneNumber(),
                nurse.getEmail(),
                nurse.getShift().name(),
                nurse.getPassword(),
                nurseID);
    }

    // Fetch nurses by shift
    public List<Nurse> getNursesByShift(Nurse.Shift shift) {
        String sql = "SELECT * FROM nurse WHERE shift = ?";
        return jdbcTemplate.query(sql, nurseRowMapper, shift.name());
    }
}
