package de.fh.albsig.id92012.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.data.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to the currently authenticated user and handles user logout.
 */
@Component
public class AuthenticatedUser {

  private final transient UserRepository userRepository;
  private final transient AuthenticationContext authenticationContext;

  /**
   * Constructor for AuthenticatedUser.
   *
   * @param authenticationContext the authentication context to retrieve authenticated user
   *        information
   * 
   * @param userRepository the repository to access user data
   */
  @Autowired
  public AuthenticatedUser(AuthenticationContext authenticationContext,
      UserRepository userRepository) {
    this.userRepository = userRepository;
    this.authenticationContext = authenticationContext;
  }

  /**
   * Retrieves the currently authenticated user.
   *
   * @return an Optional containing the authenticated user, or empty if no user is authenticated
   */
  @Transactional
  public Optional<User> get() {
    return authenticationContext.getAuthenticatedUser(UserDetails.class)
        .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
  }

  /**
   * Logs out the currently authenticated user.
   */
  public void logout() {
    authenticationContext.logout();
  }
}
