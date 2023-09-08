package hr.algebra.streamingPlatform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        logger.info("Configuring UserDetailsManager...");
        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        theUserDetailsManager
                .setUsersByUsernameQuery("select username, password, is_confirmed from user where username=?");
        theUserDetailsManager
                .setAuthoritiesByUsernameQuery("select username, role from roles where username=?");

        // For debugging: Fetching a user to log the username and password
        try {
            UserDetails userDetails = theUserDetailsManager.loadUserByUsername("john_doe");
            String username = userDetails.getUsername();
            String password = userDetails.getPassword();

            logger.debug("Fetched user: username = {}, password = {}", username, password);
        } catch (UsernameNotFoundException e) {
            logger.error("User not found", e);
        }

        return theUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Security Filter Chain...");
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/videos").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/videos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/videos").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("USER")

                        .requestMatchers(HttpMethod.GET, "/api/tags").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tags").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tags/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/countries").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/countries/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/countries").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/countries/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/countries/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/genres").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/genres").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/genres/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/images/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/images/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/images/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/notifications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/notifications/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/notifications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/notifications/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/notifications/**").hasRole("ADMIN")
        );
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF)
        http.csrf(csfr->csfr.disable());
        return http.build();
    }
}
