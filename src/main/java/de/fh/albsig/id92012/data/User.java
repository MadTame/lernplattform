package de.fh.albsig.id92012.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the application.
 * 
 * <p>The {@link User} entity contains the user's credentials and roles for authentication and
 * authorization.</p>
 */
@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {

  private String username;
  private String name;
  @JsonIgnore
  private String hashedPassword;
  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<Role> roles;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  /**
   * Gets a copy of the set of roles assigned to the user.
   *
   * @return a copy of the roles assigned to the user
   */
  public Set<Role> getRoles() {
    Set<Role> copySet = new HashSet<Role>(roles);
    return copySet;
  }

  /**
   * Sets the roles for the user.
   *
   * @param roles the roles to assign to the user
   */
  public void setRoles(Set<Role> roles) {
    Set<Role> copySet = new HashSet<Role>(roles);
    this.roles = copySet;
  }
}
