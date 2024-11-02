package dev.praneeth.backend.User;

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
            user.setUserID(rs.getInt("userID"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setDob(rs.getDate("dob").toLocalDate());
            user.setGender(User.Gender.valueOf(rs.getString("gender")));
            user.setAddress(rs.getString("address"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    // Fetch all users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // Fetch user by ID
    public Optional<User> getUserById(Integer userId) {
        String sql = "SELECT * FROM user WHERE userID = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.stream().findFirst();
    }

    // Add a new user
    public void addUser(User user) {
        String sql = "INSERT INTO user (firstName, lastName, dob, gender, address, phoneNumber, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                user.getGender().name(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword());
    }

    // Delete a user by ID
    public void deleteUser(Integer userId) {
        String sql = "DELETE FROM user WHERE userID = ?";
        jdbcTemplate.update(sql, userId);
    }

    // Update a user by ID
    public void updateUser(User user) {
        String sql = "UPDATE user SET firstName = ?, lastName = ?, dob = ?, gender = ?, address = ?, phoneNumber = ?, email = ?, password = ? WHERE userID = ?";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                user.getGender().name(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                user.getUserID());
    }

    // Fetch user by email
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.stream().findFirst();
    }
}
