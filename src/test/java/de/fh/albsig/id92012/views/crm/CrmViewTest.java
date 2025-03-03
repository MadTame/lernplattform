package de.fh.albsig.id92012.views.crm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.services.UserService;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


/**
 * The test class for CrmView.
 */
class CrmViewTest {

  private UserService userServiceMock;
  private CrmView crmView;

  @BeforeEach
  void setUp() {
    userServiceMock = Mockito.mock(UserService.class);
    // Mock data
    User user1 = new User();
    user1.setName("John Doe");
    user1.setUsername("doejohn");
    user1.setRoles(Set.of());
    User user2 = new User();
    user2.setName("Jane Doe");
    user2.setUsername("doejane");
    user2.setRoles(Set.of());
    when(userServiceMock.findAllUsers()).thenReturn(List.of(user1, user2));
    // create crmView
    crmView = new CrmView(userServiceMock);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGridIsPopulated() {

    // update Grid is called in constructor
    Grid<User> grid = crmView.getChildren()
        .filter(component -> component instanceof Grid<?>
            && ((Grid<?>) component).getBeanType().equals(User.class))
        .map(component -> (Grid<User>) component).findFirst()
        .orElseThrow(() -> new IllegalStateException("No Grid<User> found in CrmView"));

    // Verify grid content
    assertEquals(2, grid.getGenericDataView().getItems().count());
    verify(userServiceMock, times(1)).findAllUsers();
  }

  @Test
  void testCreateDialog() {
    User user1 = new User();
    user1.setName("John Doe");
    user1.setUsername("doejohn");
    user1.setRoles(Set.of());

    Dialog dialog = crmView.createDialog(user1);
    // Check if dialog is created
    assertNotNull(dialog);
  }
}
