package com.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        // ✅ Enable CORS for Angular & external clients
        security.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("*")); // ✅ Allowed origins
//            config.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1")); // ✅ Allowed origins
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ Allowed HTTP methods
            config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // ✅ Allowed headers
            config.setAllowCredentials(true);
            return config;
        }));

        // ✅ Disable CSRF for API requests (but keep it enabled for browser forms)
        security.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")); // ✅ CSRF disabled for API endpoints

        // ✅ Authentication & Authorization Rules
        security.authorizeHttpRequests(request ->
                request.requestMatchers("/api/**").authenticated() // ✅ Protect API routes
                        .anyRequest().permitAll() // ✅ Allow all other requests
        );

        // ✅ Enable JWT Authentication for OAuth2
        security.oauth2ResourceServer(authServer -> authServer.jwt(jwt -> jwt.decoder(jwtDecoder()) // Use our custom decoder
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        return security.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String jwksUri = "http://localhost:8085/realms/registration_form/protocol/openid-connect/certs";

        // ✅ Create a RestTemplate and set the correct Accept header
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().set("Accept", "application/json"); // ✅ Set correct Accept header
            return execution.execute(request, body);
        }));

        return NimbusJwtDecoder.withJwkSetUri(jwksUri)
                .restOperations(restTemplate) // ✅ Set pre-configured RestTemplate
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Adjust based on Keycloak roles claim
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
