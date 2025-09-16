package com.devocean.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // Marks this class as a Spring configuration class
@EnableWebSecurity  // Enables Spring Security in the project
public class SecurityConfig {

    // Defines the main security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Define which requests are allowed or need authentication
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET, "/api/photos/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/restaurants/**").permitAll()
                                .anyRequest().authenticated() // ALL requests require authentication
                )
                // Configure OAuth2 resource server support using JWT tokens
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                // Use a custom JWT converter to extract authorities/roles if needed
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                // Disable server-side session storage (stateless API)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Disable CSRF protection (commonly done for stateless REST APIs)
                .csrf(csrf -> csrf.disable());

        return http.build(); // Build and return the security filter chain
    }

    // Bean to convert JWT claims into Spring Security Authentication object
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
        /*
            By default, this converter:
            - Takes JWT claims (like roles, username)
            - Converts them into GrantedAuthority objects used by Spring Security
            - Allows @PreAuthorize or role-based checks in your controllers/services
        */
    }
}