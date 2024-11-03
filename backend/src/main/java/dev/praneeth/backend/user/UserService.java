package dev.praneeth.backend.User;

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
    private final UserJwtService jwtService;

    public UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder, UserJwtService jwtService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public List<User> GetUsers() {
        return userDao.getAllUsers();
    }

    public Optional<User> getUserById(Integer userID) {
        return userDao.getUserById(userID);
    }

    public void AddUser(User user) {
        Optional<User> userOptional = userDao.getUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

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
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist"));

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().trim().isEmpty()) {
            user.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null && !updateRequest.getLastName().trim().isEmpty()) {
            user.setLastName(updateRequest.getLastName());
        }

        if (updateRequest.getDob() != null && !updateRequest.getDob().trim().isEmpty()) {
            try {
                LocalDate parsedDob = LocalDate.parse(updateRequest.getDob());
                user.setDob(parsedDob);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format for dob: " + updateRequest.getDob());
            }
        }

        if (updateRequest.getGender() != null) {
            user.setGender(updateRequest.getGender());
        }

        if (updateRequest.getAddress() != null && !updateRequest.getAddress().trim().isEmpty()) {
            user.setAddress(updateRequest.getAddress());
        }

        if (updateRequest.getPhoneNumber() != null && !updateRequest.getPhoneNumber().trim().isEmpty()) {
            user.setPhoneNumber(updateRequest.getPhoneNumber());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().trim().isEmpty()) {
            Optional<User> userWithEmail = userDao.getUserByEmail(updateRequest.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getUserID().equals(userId)) {
                throw new IllegalStateException("Email already taken");
            }
            user.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        userDao.updateUser(user);
    }

    // Updated validateLogin method to return UserLoginResponse with token and role
    public Optional<UserLoginResponse> validateLogin(String email, String rawPassword) {
        Optional<User> user = userDao.getUserByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) {
            String token = jwtService.generateToken(user.get());
            String role = "patient"; // Assuming role is "USER", modify as needed
            return Optional.of(new UserLoginResponse(
                    user.get().getUserID(),
                    user.get().getFirstName(),
                    user.get().getLastName(),
                    user.get().getEmail(),
                    token,
                    role));
        }
        return Optional.empty();
    }
}
