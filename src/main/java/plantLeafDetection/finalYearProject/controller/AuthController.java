package plantLeafDetection.finalYearProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, request.getUsername());
            if (count != null && count > 0) {
                response.put("success", false);
                response.put("message", "User already exists");
                return ResponseEntity.badRequest().body(response);
            }
            jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?)",
                    request.getUsername(), request.getPassword());
            response.put("success", true);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> users = jdbcTemplate.queryForList(
                    "SELECT username, password FROM users WHERE username = ?", request.getUsername());

            if (!users.isEmpty()) {
                String storedPassword = (String) users.get(0).get("password");
                if (storedPassword.equals(request.getPassword())) {
                    response.put("success", true);
                    response.put("message", "Login successful");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid password");
                    return ResponseEntity.status(401).body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, request.getUsername());
            response.put("exists", count != null && count > 0);
        } catch (Exception e) {
            response.put("exists", false);
        }
        return ResponseEntity.ok(response);
    }
}

// DTO
class LoginRequest {
    private String username;
    private String password;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
