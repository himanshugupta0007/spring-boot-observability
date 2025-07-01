package com.observability.controller;

import com.observability.entity.User;
import com.observability.service.UserService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @Observed(name = "user.controller.getAll", contextualName = "get-all-users-endpoint")
    @Timed(value = "user.controller.getAll.time", description = "Time taken to get all users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("GET /api/users - Retrieving all users");
        
        List<User> users = userService.getAllUsers();
        logger.info("Returning {} users", users.size());
        
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @Observed(name = "user.controller.getById", contextualName = "get-user-by-id-endpoint")
    @Timed(value = "user.controller.getById.time", description = "Time taken to get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("GET /api/users/{} - Retrieving user by ID", id);
        
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", id);
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/username/{username}")
    @Observed(name = "user.controller.getByUsername", contextualName = "get-user-by-username-endpoint")
    @Timed(value = "user.controller.getByUsername.time", description = "Time taken to get user by username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("GET /api/users/username/{} - Retrieving user by username", username);
        
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            logger.info("User found with username: {}", username);
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with username: {}", username);
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Observed(name = "user.controller.create", contextualName = "create-user-endpoint")
    @Timed(value = "user.controller.create.time", description = "Time taken to create user")
    @Counted(value = "user.controller.create.count", description = "Number of user creation requests")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("POST /api/users - Creating new user with username: {}", user.getUsername());
        
        try {
            User createdUser = userService.createUser(user);
            logger.info("Successfully created user with ID: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            logger.error("Failed to create user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Observed(name = "user.controller.update", contextualName = "update-user-endpoint")
    @Timed(value = "user.controller.update.time", description = "Time taken to update user")
    @Counted(value = "user.controller.update.count", description = "Number of user update requests")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("PUT /api/users/{} - Updating user", id);
        
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            logger.info("Successfully updated user with ID: {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Failed to update user with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Observed(name = "user.controller.delete", contextualName = "delete-user-endpoint")
    @Timed(value = "user.controller.delete.time", description = "Time taken to delete user")
    @Counted(value = "user.controller.delete.count", description = "Number of user deletion requests")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/{} - Deleting user", id);
        
        try {
            userService.deleteUser(id);
            logger.info("Successfully deleted user with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete user with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    @Observed(name = "user.controller.search", contextualName = "search-users-endpoint")
    @Timed(value = "user.controller.search.time", description = "Time taken to search users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String firstName) {
        logger.info("GET /api/users/search?firstName={} - Searching users", firstName);
        
        List<User> users = userService.searchUsersByFirstName(firstName);
        logger.info("Found {} users matching search criteria", users.size());
        
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check endpoint called");
        return ResponseEntity.ok("User service is healthy!");
    }
}