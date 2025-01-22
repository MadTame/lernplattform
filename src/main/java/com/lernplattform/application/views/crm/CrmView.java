package com.lernplattform.application.views.crm;

import java.util.List;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.lernplattform.application.data.Role;
import com.lernplattform.application.data.User;
import com.lernplattform.application.services.UserService;
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
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Crm")
@Route("crm")
@Menu(order = 3, icon = LineAwesomeIconUrl.TH_SOLID)
@RolesAllowed("ADMIN")
public class CrmView extends Div {

  private static final long serialVersionUID = 1L;

  private final transient UserService userService;

  private Grid<User> grid;

  public CrmView(UserService userService) {
    this.userService = userService;

    this.addClassName("crm-view");
    this.setSizeFull();
    this.createGrid();
    this.createListener();
    this.add(this.grid);
  }

  private void createGrid() {
    this.grid = new Grid<>(User.class, false);
    grid.addColumn(User::getUsername).setHeader("Username");
    grid.addColumn(User::getName).setHeader("Full name");
    grid.addColumn(User::getRoles).setHeader("Roles");
    this.grid.setSelectionMode(SelectionMode.SINGLE);
    this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
    this.grid.setHeight("100%");
    updateGrid();
  }

  private void updateGrid() {
    List<User> users = this.userService.findAllUsers();
    this.grid.setItems(users);
  }

  private void createListener() {
    this.grid.addItemDoubleClickListener(event -> {

      this.createDialog(event.getItem()).open();

    });
  }

  private Dialog createDialog(User user) {
    // Dialog
    Dialog dialog = new Dialog();
    dialog.setHeaderTitle("Edit User");

    // Form components
    TextField nameField = new TextField("Name");
    nameField.setValue(user.getName());
    nameField.setWidthFull();

    TextField usernameField = new TextField("Username");
    usernameField.setValue(user.getUsername());
    usernameField.setWidthFull();

    // CheckboxGroup for multiple roles
    CheckboxGroup<Role> rolesCheckboxGroup = new CheckboxGroup<>();
    rolesCheckboxGroup.setLabel("Roles");
    rolesCheckboxGroup.setItems(Role.values()); // Use Enum values directly
    rolesCheckboxGroup.setValue(user.getRoles()); // Convert existing roles from String to Enum
    rolesCheckboxGroup.setWidthFull();

    // Binder for validation
    Binder<User> binder = new Binder<>(User.class);
    binder.forField(nameField).asRequired("Name is required").bind(User::getName, User::setName);
    binder.forField(usernameField).asRequired("Username is required").bind(User::getUsername,
        User::setUsername);
    binder.forField(rolesCheckboxGroup).asRequired("At least one role is required")
        .bind(User::getRoles, User::setRoles);

    // Layout
    FormLayout formLayout = new FormLayout();
    formLayout.add(nameField, usernameField, rolesCheckboxGroup);

    // Buttons
    Button saveButton = new Button("Save", e -> {
      if (binder.writeBeanIfValid(user)) {
        // Call your service to save changes
        userService.saveUser(user);
        Notification.show("User updated successfully");
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

    // Add components to the dialog
    dialog.add(formLayout, buttonsLayout);

    return dialog;
  }
}
