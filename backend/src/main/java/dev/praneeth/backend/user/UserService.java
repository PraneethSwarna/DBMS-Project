package dev.praneeth.backend.user;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> GetUsers() {
        return userDao.getAllUsers();
    }

    public void AddUser(User user) {
        Optional<User> userOptional = userDao.getUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        // Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.addUser(user);
    }

    public void DeleteUser(Integer userId) {
        boolean exists = userDao.getUserById(userId).isPresent();
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userDao.deleteUser(userId);
    }

    public void updateUser(Integer userId, UserUpdateRequest updateRequest) {
        // Retrieve the user or throw an exception if not found
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist"));

        // Update firstName if it's valid
        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().trim().isEmpty()) {
            user.setFirstName(updateRequest.getFirstName());
        }

        // Update lastName if it's valid
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().trim().isEmpty()) {
            user.setLastName(updateRequest.getLastName());
        }

        // Update dob if it's valid
        if (updateRequest.getDob() != null && !updateRequest.getDob().trim().isEmpty()) {
            try {
                LocalDate parsedDob = LocalDate.parse(updateRequest.getDob()); // Convert dob from String to LocalDate
                user.setDob(parsedDob);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format for dob: " + updateRequest.getDob());
            }
        }

        // Update gender if it's not null
        if (updateRequest.getGender() != null) {
            user.setGender(updateRequest.getGender());
        }

        // Update address if it's valid
        if (updateRequest.getAddress() != null && !updateRequest.getAddress().trim().isEmpty()) {
            user.setAddress(updateRequest.getAddress());
        }

        // Update phone_number if it's valid
        if (updateRequest.getPhone_number() != null && !updateRequest.getPhone_number().trim().isEmpty()) {
            user.setPhone_number(updateRequest.getPhone_number());
        }

        // Check for duplicate email before updating
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().trim().isEmpty()) {
            Optional<User> userWithEmail = userDao.getUserByEmail(updateRequest.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getPatientID().equals(userId)) {
                throw new IllegalStateException("Email already taken");
            }
            user.setEmail(updateRequest.getEmail());
        }

        // If password is provided, hash it before saving
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        // Save the updated user entity
        userDao.updateUser(user);
    }
}
