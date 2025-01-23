package com.lernplattform.application.services;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.lernplattform.application.data.User;
import com.lernplattform.application.data.UserRepository;


@Service
public class UserService {

  private static final Logger logger = LogManager.getLogger(UserService.class); // Add logger

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    logger.debug("UserService initialized with UserRepository."); // Logging
  }

  public List<User> findAllUsers() {
    logger.debug("Fetching all users.");
    List<User> users = userRepository.findAll();
    logger.info("Found {} users.", users.size());
    return users;
  }

  public User findByUsername(String username) {
    logger.debug("Finding user by username: {}", username);
    User user = userRepository.findByUsername(username);
    logger.info("User found with username: {}", username);
    return user;
  }

  public boolean isUsernameTaken(String username) {
    logger.debug("Checking if username '{}' is taken.", username);
    try {
      this.findByUsername(username);
      logger.info("Username '{}' is already taken.", username);
      return true;
    } catch (EmptyResultDataAccessException ex) {
      logger.info("Username '{}' is available.", username);
      return false;
    }
  }

  public long countUsers() {
    logger.debug("Counting the total number of users.");
    long count = userRepository.count();
    logger.info("Total users count: {}", count);
    return count;
  }

  public void deleteUser(User user) {
    logger.debug("Deleting user: {}", user.getUsername());
    userRepository.delete(user);
    logger.info("User '{}' deleted successfully.", user.getUsername());
  }

  public void saveUser(User user) {
    logger.debug("Saving user: {}", user.getUsername());
    userRepository.save(user);
    logger.info("User '{}' saved successfully.", user.getUsername());
  }
}
