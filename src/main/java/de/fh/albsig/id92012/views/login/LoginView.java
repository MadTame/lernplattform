package de.fh.albsig.id92012.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.fh.albsig.id92012.views.register.RegisterView;


/**
 * The login view for the user authentication screen.
 * 
 * <p>This view contains a login form and a button to navigate to the registration screen.</p>
 */

@PageTitle("Login")
@Route("login")
@Menu(order = 0, icon = "line-awesome/svg/user-circle-solid.svg")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

  private static final long serialVersionUID = 1L;
  private Button register;

  /**
   * Constructs the login view and initializes the login form and the register button.
   */
  public LoginView() {

    var login = new LoginForm();
    login.setAction("login");
    login.setForgotPasswordButtonVisible(false);

    this.register = new Button("Create new User");
    this.register.setWidth("17rem");
    this.register
        .addClickListener(e -> register.getUI().ifPresent(ui -> ui.navigate(RegisterView.class)));

    this.add(login, this.register);
    this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    this.setSizeFull();
    this.setMargin(false);
  }
}


