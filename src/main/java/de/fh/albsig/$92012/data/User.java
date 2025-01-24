package de.fh.albsig.$92012.data;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

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

  public Set<Role> getRoles() {
    Set<Role> copySet = new HashSet<Role>(roles);
    return copySet;
  }

  public void setRoles(Set<Role> roles) {
    Set<Role> copySet = new HashSet<Role>(roles);
    this.roles = copySet;
  }
}
