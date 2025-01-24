package de.fh.albsig.id92012.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import de.fh.albsig.id92012.views.login.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration for the web application. Configures authentication, authorization, and
 * login view for the application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

  /**
   * Bean for PasswordEncoder using BCrypt hashing algorithm.
   *
   * @return a BCryptPasswordEncoder instance for encoding passwords
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Permit all requests for images
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

    // Permit all requests for SVG icons
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

    // Apply Vaadin's default security configuration
    super.configure(http);

    // Set the login view for the application
    setLoginView(http, LoginView.class);
  }
}
