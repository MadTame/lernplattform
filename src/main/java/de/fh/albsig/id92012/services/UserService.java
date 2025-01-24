package de.fh.albsig.id92012.services;

import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.data.UserRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * Service class to handle user-related operations. Provides methods to manage users, including
 * finding, saving, deleting, and checking usernames.
 */
@Service
public class UserService {

  private static final Logger logger = LogManager.getLogger(UserService.class);

  private final UserRepository userRepository;

  /**
   * Constructs a new UserService with the specified UserRepository.
   *
   * @param userRepository the repository to interact with the user data
   */
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    logger.debug("UserService initialized with UserRepository.");
  }

  /**
   * Retrieves a list of all users.
   *
   * @return a list of all users
   */
  public List<User> findAllUsers() {
    logger.debug("Fetching all users.");
    List<User> users = userRepository.findAll();
    logger.info("Found {} users.", users.size());
    return users;
  }

  /**
   * Finds a user by their username.
   *
   * @param username the username of the user to be found
   * @return the user with the specified username
   */
  public User findByUsername(String username) {
    logger.debug("Finding user by username: {}", username);
    User user = userRepository.findByUsername(username);
    logger.info("User found with username: {}", username);
    return user;
  }

  /**
   * Checks if a username is already taken.
   *
   * @param username the username to check
   * @return true if the username is taken, false otherwise
   */
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

  /**
   * Counts the total number of users in the repository.
   *
   * @return the total number of users
   */
  public long countUsers() {
    logger.debug("Counting the total number of users.");
    long count = userRepository.count();
    logger.info("Total users count: {}", count);
    return count;
  }

  /**
   * Deletes a user from the repository.
   *
   * @param user the user to be deleted
   */
  public void deleteUser(User user) {
    logger.debug("Deleting user: {}", user.getUsername());
    userRepository.delete(user);
    logger.info("User '{}' deleted successfully.", user.getUsername());
  }

  /**
   * Saves a user to the repository.
   *
   * @param user the user to be saved
   */
  public void saveUser(User user) {
    logger.debug("Saving user: {}", user.getUsername());
    userRepository.save(user);
    logger.info("User '{}' saved successfully.", user.getUsername());
  }
}
