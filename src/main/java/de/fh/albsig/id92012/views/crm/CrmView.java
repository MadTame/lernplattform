package de.fh.albsig.id92012.views.crm;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.fh.albsig.id92012.data.Role;
import de.fh.albsig.id92012.data.User;
import de.fh.albsig.id92012.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 * View class for managing users in the CRM system. Displays a grid of users, allows editing via
 * dialog, and integrates with the user service.
 */
@PageTitle("Crm")
@Route("crm")
@Menu(order = 3, icon = LineAwesomeIconUrl.TH_SOLID)
@RolesAllowed("ADMIN")
public class CrmView extends Div {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LogManager.getLogger(CrmView.class);

  private final transient UserService userService;

  private Grid<User> grid;

  /**
   * Constructs a new CrmView instance.
   *
   * @param userService the user service for managing users
   */
  public CrmView(UserService userService) {
    this.userService = Objects.requireNonNull(userService, "userService must not be null");

    this.addClassName("crm-view");
    this.setSizeFull();
    this.createGrid();
    this.createListener();
    this.add(this.grid);
  }

  private void createGrid() {
    this.grid = new Grid<>(User.class, false);
    this.grid.addColumn(User::getUsername).setHeader("Username");
    this.grid.addColumn(User::getName).setHeader("Full name");
    this.grid.addColumn(User::getRoles).setHeader("Roles");
    this.grid.setSelectionMode(SelectionMode.SINGLE);
    this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
    this.grid.setHeight("100%");
    updateGrid();
  }

  /**
   * Updates the grid with the latest user data.
   */
  public void updateGrid() {
    List<User> users = this.userService.findAllUsers();
    this.grid.setItems(users);
  }

  private void createListener() {
    this.grid.addItemDoubleClickListener(event -> {
      this.createDialog(event.getItem()).open();
    });
  }

  Dialog createDialog(User user) {
    Dialog dialog = new Dialog();
    dialog.setHeaderTitle("Edit User");

    // Form components
    TextField nameField = new TextField("Name");
    nameField.setValue(user.getName());
    nameField.setWidthFull();

    TextField usernameField = new TextField("Username");
    usernameField.setValue(user.getUsername());
    usernameField.setWidthFull();

    CheckboxGroup<Role> rolesCheckboxGroup = new CheckboxGroup<>();
    rolesCheckboxGroup.setLabel("Roles");
    rolesCheckboxGroup.setItems(Role.values());
    rolesCheckboxGroup.setValue(user.getRoles());
    rolesCheckboxGroup.setWidthFull();

    Binder<User> binder = new Binder<>(User.class);
    binder.forField(nameField).asRequired("Name is required").bind(User::getName, User::setName);
    binder.forField(usernameField).asRequired("Username is required").bind(User::getUsername,
        User::setUsername);
    binder.forField(rolesCheckboxGroup).asRequired("At least one role is required")
        .bind(User::getRoles, User::setRoles);

    FormLayout formLayout = new FormLayout();
    formLayout.add(nameField, usernameField, rolesCheckboxGroup);

    Button saveButton = new Button("Save", e -> {
      if (binder.writeBeanIfValid(user)) {
        try {
          userService.saveUser(user);
          Notification.show("User updated successfully");
          logger.info("User " + user.getUsername() + " was saved successfully");
        } catch (Exception exc) {
          logger.warn("An error occurred during save event of user " + user.getUsername());
        }

        dialog.close();
        updateGrid();
      } else {
        Notification.show("Please correct the errors in the form");
      }
    });
    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    Button cancelButton = new Button("Cancel", e -> dialog.close());
    cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    Div buttonsLayout = new Div(cancelButton, saveButton);
    buttonsLayout.getStyle().set("display", "flex");
    buttonsLayout.getStyle().set("gap", "var(--lumo-space-m)");
    buttonsLayout.getStyle().set("margin-top", "var(--lumo-space-m)");

    dialog.add(formLayout, buttonsLayout);

    return dialog;
  }
}
