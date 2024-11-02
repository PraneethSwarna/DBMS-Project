package dev.praneeth.backend.User;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> GetUsers() {
        return userService.GetUsers();
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userID) {
        Optional<User> user = userService.getUserById(userID); // Get the Optional<User>

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Return the User if present
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if User not found
        }
    }

    @PostMapping
    public void AddUser(@RequestBody User user) {
        userService.AddUser(user);
    }

    @DeleteMapping(path = "/{userID}")
    public void DeleteUser(@PathVariable("userID") Integer userID) {
        userService.DeleteUser(userID);
    }

    @PutMapping(path = "/{userID}")
    public void UpdateUser(@PathVariable("userID") Integer userID, @RequestBody UserUpdateRequest updateRequest) {
        userService.updateUser(userID, updateRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginRequest loginRequest) {
        Optional<UserLoginResponse> response = userService.validateLogin(loginRequest.getEmail(),
                loginRequest.getPassword());

        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    // Logout does not require backend processing since we are using JWT
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("Successfully logged out. Please remove the token on the client side.");
    }
}
