package com.lernplattform.application.views.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.lernplattform.application.data.User;
import com.lernplattform.application.services.UserService;
import com.lernplattform.application.views.login.LoginView;
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

@PageTitle("Register")
@Route("register")
@Menu(order = 1, icon = "line-awesome/svg/user-circle-solid.svg")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {


  private static final long serialVersionUID = 1L;
  private TextField firstName;
  private TextField lastName;
  private PasswordField password;
  private Button register;
  private transient final UserService userService;
  @Autowired
  private transient final PasswordEncoder passwordEncoder;

  public RegisterView(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
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
      if (firstName.getValue() != null && lastName.getValue() != null
          && password.getValue() != null) {
        register(firstName.getValue(), lastName.getValue(), password.getValue());
      } else {
        Notification.show("Please fill out all required fields.");
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
    Notification.show("Created user successfully. Your username is: " + user.getUsername());
    UI.getCurrent().navigate(LoginView.class);


  }

  private String parseUsername(String firstName, String lastName) {
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
