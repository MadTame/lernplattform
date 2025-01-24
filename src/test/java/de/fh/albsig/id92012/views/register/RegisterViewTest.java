package de.fh.albsig.id92012.views.register;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.services.UserService;

public class RegisterViewTest {

  @Mock
  private UserService userService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private RegisterView registerView;

  private TextField firstNameField;
  private TextField lastNameField;
  private PasswordField passwordField;
  private Button registerButton;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Initializes mocks
    registerView = new RegisterView(userService, passwordEncoder);
  }

  @AfterEach
  void tearDown() {
    // Any necessary cleanup
  }

  @Test
  void testRegisterUserValidData() {
    // Given valid user input
    String firstName = "John";
    String lastName = "Doe";
    String password = "password123";

    registerView.firstName.setValue(firstName);
    registerView.lastName.setValue(lastName);
    registerView.password.setValue(password);

    // Mock password encoding
    when(passwordEncoder.encode(password)).thenReturn("hashedPassword123");

    // Mock userService to accept user saving
    when(userService.isUsernameTaken(anyString())).thenReturn(false);

    // Trigger the register button click
    registerView.register.click();

    // Verify the user is created and saved
    verify(userService, times(1)).saveUser(any(User.class));
  }

  @Test
  void testUsernameParsingWithLongLastName() {
    // Given long last name
    String firstName = "John";
    String lastName = "Longlastname";
    String parsedUsername = registerView.parseUsername(firstName, lastName);
    assertEquals("longlasj", parsedUsername);

    firstName = "Max";
    lastName = "Mustermann";
    parsedUsername = registerView.parseUsername(firstName, lastName);
    assertEquals("mustermm", parsedUsername);
  }

  @Test
  void testUsernameParsingWithShortLastName() {
    // Given short last name
    String firstName = "John";
    String lastName = "Li";
    String parsedUsername = registerView.parseUsername(firstName, lastName);
    assertEquals("lijohn", parsedUsername);

    firstName = "Johnathan";
    lastName = "Li";
    parsedUsername = registerView.parseUsername(firstName, lastName);
    assertEquals("lijohnat", parsedUsername);
  }

  @Test
  void testRegisterUserWithMissingFields() {
    // Given missing user input (e.g., no first name)
    registerView.firstName.setValue("");
    registerView.lastName.setValue("Doe");
    registerView.password.setValue("password123");

    // Trigger the register button click
    registerView.register.click();

    // Verify the user was not saved
    verify(userService, times(0)).saveUser(any(User.class));

    // Test with whitespace
    registerView.firstName.setValue("   ");

    // Trigger the register button click
    registerView.register.click();

    // Verify the user was not saved
    verify(userService, times(0)).saveUser(any(User.class));

  }
}
