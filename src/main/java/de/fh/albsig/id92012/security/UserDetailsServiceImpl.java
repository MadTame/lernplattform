package de.fh.albsig.id92012.security;

import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.data.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserDetailsService interface for loading user details from the database.
 * Provides authentication and role-based authorization support.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Constructs a UserDetailsServiceImpl with the given UserRepository.
   *
   * @param userRepository the repository used to retrieve user data
   */
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Loads a user by their username and returns a UserDetails object. If the user is not found,
   * throws a UsernameNotFoundException.
   *
   * @param username the username of the user to load
   * @return UserDetails the details of the found user
   * @throws UsernameNotFoundException if the user with the given username is not found
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      User user = userRepository.findByUsername(username);
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
          user.getHashedPassword(), getAuthorities(user));
    } catch (EmptyResultDataAccessException ex) {
      throw new UsernameNotFoundException("No user present with username: " + username);
    }
  }

  private static List<GrantedAuthority> getAuthorities(User user) {
    return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }
}
