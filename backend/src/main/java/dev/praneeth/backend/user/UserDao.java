package dev.praneeth.backend.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for User object
    private RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setPatientID(rs.getInt("patientID"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setDob(rs.getDate("dob").toLocalDate());
            user.setGender(User.Gender.valueOf(rs.getString("gender")));
            user.setAddress(rs.getString("address"));
            user.setPhone_number(rs.getString("phone_number"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    // Fetch all users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // Fetch user by ID
    public Optional<User> getUserById(Integer userId) {
        String sql = "SELECT * FROM users WHERE patientID = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.stream().findFirst();
    }

    // Add a new user
    public void addUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName, dob, gender, address, phone_number, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                user.getGender().name(),
                user.getAddress(),
                user.getPhone_number(),
                user.getEmail(),
                user.getPassword());
    }

    // Delete a user by ID
    public void deleteUser(Integer userId) {
        String sql = "DELETE FROM users WHERE patientID = ?";
        jdbcTemplate.update(sql, userId);
    }

    // Update a user by ID
    public void updateUser(User user) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, dob = ?, gender = ?, address = ?, phone_number = ?, email = ?, password = ? WHERE patientID = ?";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                user.getGender().name(),
                user.getAddress(),
                user.getPhone_number(),
                user.getEmail(),
                user.getPassword(),
                user.getPatientID());
    }

    // Fetch user by email
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.stream().findFirst();
    }
}
