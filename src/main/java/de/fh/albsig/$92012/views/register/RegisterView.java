package de.fh.albsig.$92012.views.register;

import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.fh.albsig.$92012.data.User;
import de.fh.albsig.$92012.services.UserService;
import de.fh.albsig.$92012.views.login.LoginView;
import io.micrometer.common.util.StringUtils;

@PageTitle("Register")
@Route("register")
@Menu(order = 1, icon = "line-awesome/svg/user-circle-solid.svg")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

  private static final long serialVersionUID = 1L;
  TextField firstName;
  TextField lastName;
  PasswordField password;
  Button register;
  private transient final UserService userService;
  @Autowired
  private transient final PasswordEncoder passwordEncoder;

  private static final Logger logger = LogManager.getLogger(RegisterView.class);


  public RegisterView(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = Objects.requireNonNull(userService, "userService must not be null");
    this.passwordEncoder = passwordEncoder;

    this.firstName = new TextField();
    this.firstName.setPlaceholder("First name");
    this.firstName.setWidth("17rem");
    this.firstName.setRequired(true);
    this.lastName = new TextField();
    this.lastName.setPlaceholder("Last name");
    this.lastName.setWidth("17rem");
    this.lastName.setRequired(true);
    this.password = new PasswordField();
    this.password.setPlaceholder("Password");
    this.password.setWidth("17rem");
    this.password.setRequired(true);
    this.register = new Button("Register");
    this.register.setWidth("17rem");
    this.register.addClickListener(event -> {
      if (StringUtils.isNotBlank(firstName.getValue())
          && StringUtils.isNotBlank(lastName.getValue())
          && StringUtils.isNotBlank(password.getValue())) {
        register(firstName.getValue(), lastName.getValue(), password.getValue());
      } else {
        try {
          Notification.show("Please fill out all required fields.");
        } catch (Exception e) {
          logger.info("Notification event in registerView failed, or was invoked during unit test");
        }
      }
    });

    this.add(this.firstName, this.lastName, this.password, this.register);
    this.setAlignSelf(Alignment.CENTER, this.firstName, this.lastName, this.password,
        this.register);
    this.setSizeFull();
    this.setMargin(false);

  }

  private void register(String firstName, String lastName, String password) {
    User user = new User();
    user.setName(firstName + " " + lastName);
    user.setUsername(this.parseUsername(firstName, lastName));
    user.setHashedPassword(passwordEncoder.encode(password));

    int counter = 1;
    while (this.userService.isUsernameTaken(user.getUsername())) {
      user.setUsername(user.getUsername() + counter);
    }

    userService.saveUser(user);
    try {
      Notification.show("Created user successfully. Your username is: " + user.getUsername());
      UI.getCurrent().navigate(LoginView.class);
    } catch (Exception e) {
      logger.info("Notification event in registerView failed, or was invoked during unit test");
    }

  }

  String parseUsername(String firstName, String lastName) {
    firstName = firstName.trim().toLowerCase();
    lastName = lastName.trim().toLowerCase();

    if (lastName.length() >= 8) {
      return lastName.substring(0, 7) + firstName.charAt(0);
    } else {
      int remainingLength = 8 - lastName.length();
      return lastName + firstName.substring(0, Math.min(remainingLength, firstName.length()));
    }
  }

}
