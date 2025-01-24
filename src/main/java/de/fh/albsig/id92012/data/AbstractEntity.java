package de.fh.albsig.id92012.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

/**
 * Abstract base class for entities that require an ID and version.
 * 
 * <p>This class provides a common identifier and versioning mechanism for entities in the
 * application. It ensures that all subclasses have a unique ID and a version for optimistic
 * locking.</p>
 * 
 * <p>The {@link #id} is generated using a sequence generator, which starts at 1000 to accommodate
 * pre-existing data from the demo (`data.sql`). The {@link #version} field is used for optimistic
 * locking.</p>
 */
@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
  // The initial value is to account for data.sql demo data ids
  @SequenceGenerator(name = "idgenerator", initialValue = 1000)
  private Long id;

  @Version
  private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractEntity that)) {
      return false; // null or not an AbstractEntity class
    }
    return getId().equals(that.getId());
  }
}
