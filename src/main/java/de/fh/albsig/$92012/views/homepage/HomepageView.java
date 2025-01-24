package de.fh.albsig.$92012.views.homepage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Homepage")
@Route("")
@Menu(order = 2, icon = "line-awesome/svg/globe-solid.svg")
@PermitAll
public class HomepageView extends VerticalLayout {

  private static final long serialVersionUID = 1L;

  public HomepageView() {
    // Set up the overall layout
    this.setSizeFull();
    this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    this.setPadding(false);
    this.setSpacing(false);
    this.getStyle().set("padding-left", "1rem");

    // Create the header
    Header header = createHeader();

    // Create the main content
    Main mainContent = createMainContent();

    // Create the footer
    Footer footer = createFooter();

    // Add all components to the layout
    add(header, mainContent, footer);
  }

  private Header createHeader() {
    H1 title = new H1("Welcome to the Homepage");
    title.getStyle().set("margin", "0");

    Button aboutButton = new Button("About", e -> {
      // Add navigation logic here if needed
    });

    Button contactButton = new Button("Contact", e -> {
      // Add navigation logic here if needed
    });

    HorizontalLayout navBar = new HorizontalLayout(aboutButton, contactButton);
    navBar.setSpacing(true);

    Header header = new Header(title, navBar);
    header.getStyle().set("padding", "1rem");
    header.getStyle().set("background-color", "#f3f4f6");
    header.setWidthFull();

    return header;
  }

  private Main createMainContent() {
    H1 mainTitle = new H1("Welcome to the homepage!");
    mainTitle.getStyle().set("margin-bottom", "1rem");

    Paragraph mainText = new Paragraph(
        "This is the main content area where you can sign up for courses" + " and exams");

    Button learnMoreButton = new Button("Learn More", e -> {
      // Add action logic here
    });

    VerticalLayout contentLayout = new VerticalLayout(mainTitle, mainText, learnMoreButton);
    contentLayout.setSpacing(true);
    contentLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    contentLayout.setHeightFull();

    Main mainContent = new Main(contentLayout);
    mainContent.getStyle().set("padding", "2rem");
    mainContent.setHeightFull();

    return mainContent;
  }

  private Footer createFooter() {
    Paragraph footerText = new Paragraph("© 2025 Lernplattform. All rights reserved.");
    footerText.getStyle().set("margin", "0");

    Footer footer = new Footer(footerText);
    footer.getStyle().set("padding", "1rem");
    footer.getStyle().set("background-color", "#f3f4f6");
    footer.setWidthFull();

    return footer;
  }
}

