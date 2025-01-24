package de.fh.albsig.id92012.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for {@link User} entity. Provides CRUD operations and custom queries related
 * to the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


  /**
   * Finds a User by their username.
   *
   * @param username the username of the user to find
   * 
   * @return the user with the given username, or null if no user found
   */
  User findByUsername(String username);
}
