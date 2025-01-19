package com.lernplattform.application.views.login;

import com.lernplattform.application.views.register.RegisterView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle("Login")
@Route("login")
@Menu(order = 0, icon = "line-awesome/svg/user-circle-solid.svg")
@AnonymousAllowed

public class LoginView extends VerticalLayout {

    private Button register;

    public LoginView() {

        var login = new LoginForm();
        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        this.register = new Button("Create new User");
        this.register.setWidth("17rem");
        this.register.addClickListener(e ->
                register.getUI().ifPresent(ui ->
                        ui.navigate(RegisterView.class)));


        this.add(login, this.register);
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        this.setSizeFull();
        this.setMargin(false);
    }

}

/*
@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Lernplattform");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}

 */
