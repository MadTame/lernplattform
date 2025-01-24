package de.fh.albsig.$92012.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.vaadin.flow.spring.security.AuthenticationContext;
import de.fh.albsig.$92012.data.User;
import de.fh.albsig.$92012.data.UserRepository;

@Component
public class AuthenticatedUser {


  private transient final UserRepository userRepository;
  @Autowired
  private transient final AuthenticationContext authenticationContext;

  public AuthenticatedUser(AuthenticationContext authenticationContext,
      UserRepository userRepository) {
    this.userRepository = userRepository;
    this.authenticationContext = authenticationContext;
    // this.authenticationContext =
    // Objects.requireNonNull(authenticationContext, "authenticationContext must not be null");

  }

  @Transactional
  public Optional<User> get() {
    return authenticationContext.getAuthenticatedUser(UserDetails.class)
        .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
  }

  public void logout() {
    authenticationContext.logout();
  }
}
