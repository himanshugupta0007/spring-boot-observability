package com.observability.service;

import com.observability.entity.User;
import com.observability.repo.UserRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final Random random = new Random();
    
    @Autowired
    private UserRepository userRepository;
    
    @Observed(name = "user.service.create", contextualName = "user-creation")
    @Timed(value = "user.service.create.time", description = "Time taken to create user")
    @Counted(value = "user.service.create.count", description = "Number of users created")
    public User createUser(User user) {
        logger.info("Creating user with username: {}", user.getUsername());
        
        // Simulate some processing time
        simulateProcessingDelay();
        
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("User with username {} already exists", user.getUsername());
            throw new RuntimeException("User already exists with username: " + user.getUsername());
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("User with email {} already exists", user.getEmail());
            throw new RuntimeException("User already exists with email: " + user.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        logger.info("Successfully created user with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    @Observed(name = "user.service.findAll", contextualName = "user-list-retrieval")
    @Timed(value = "user.service.findAll.time", description = "Time taken to retrieve all users")
    public List<User> getAllUsers() {
        logger.debug("Retrieving all users");
        
        simulateProcessingDelay();
        
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());
        
        return users;
    }
    
    @Observed(name = "user.service.findById", contextualName = "user-retrieval-by-id")
    @Timed(value = "user.service.findById.time", description = "Time taken to find user by ID")
    public Optional<User> getUserById(Long id) {
        logger.debug("Finding user by ID: {}", id);
        
        simulateProcessingDelay();
        
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("Found user with ID: {}", id);
        } else {
            logger.warn("User not found with ID: {}", id);
        }
        
        return user;
    }
    
    @Observed(name = "user.service.findByUsername", contextualName = "user-retrieval-by-username")
    @Timed(value = "user.service.findByUsername.time", description = "Time taken to find user by username")
    public Optional<User> getUserByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        
        simulateProcessingDelay();
        
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            logger.info("Found user with username: {}", username);
        } else {
            logger.warn("User not found with username: {}", username);
        }
        
        return user;
    }
    
    @Observed(name = "user.service.update", contextualName = "user-update")
    @Timed(value = "user.service.update.time", description = "Time taken to update user")
    @Counted(value = "user.service.update.count", description = "Number of users updated")
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with ID: {}", id);
        
        simulateProcessingDelay();
        
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            logger.error("User not found with ID: {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        User user = userOptional.get();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        
        User updatedUser = userRepository.save(user);
        logger.info("Successfully updated user with ID: {}", id);
        
        return updatedUser;
    }
    
    @Observed(name = "user.service.delete", contextualName = "user-deletion")
    @Timed(value = "user.service.delete.time", description = "Time taken to delete user")
    @Counted(value = "user.service.delete.count", description = "Number of users deleted")
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        simulateProcessingDelay();
        
        if (!userRepository.existsById(id)) {
            logger.error("User not found with ID: {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
        logger.info("Successfully deleted user with ID: {}", id);
    }
    
    @Observed(name = "user.service.search", contextualName = "user-search")
    @Timed(value = "user.service.search.time", description = "Time taken to search users")
    public List<User> searchUsersByFirstName(String firstName) {
        logger.debug("Searching users by first name: {}", firstName);
        
        simulateProcessingDelay();
        
        List<User> users = userRepository.findByFirstNameContainingIgnoreCase(firstName);
        logger.info("Found {} users with first name containing: {}", users.size(), firstName);
        
        return users;
    }
    
    /**
     * Simulate processing delay for demonstration purposes
     */
    private void simulateProcessingDelay() {
        try {
            // Random delay between 50-200ms
            Thread.sleep(50 + random.nextInt(150));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Processing interrupted");
        }
    }
}