package de.fh.albsig.id92012;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The entry point of the Spring Boot application. Use the @PWA annotation make the application
 * installable on phones, tablets and some desktop browsers.
 */
@SpringBootApplication
@Theme(value = "lernplattform")
public class Application implements AppShellConfigurator {

  private static final long serialVersionUID = 1L;

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
