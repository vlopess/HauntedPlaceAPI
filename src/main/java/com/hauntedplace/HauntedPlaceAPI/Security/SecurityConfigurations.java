package com.hauntedplace.HauntedPlaceAPI.Security;


import com.hauntedplace.HauntedPlaceAPI.Security.filters.AuthenticationFilter;
import com.hauntedplace.HauntedPlaceAPI.Security.filters.ExceptionHandlingFilter;
import com.hauntedplace.HauntedPlaceAPI.Security.filters.JWTAuthorizationFilter;
import com.hauntedplace.HauntedPlaceAPI.Services.JWTokenService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigurations {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationFilter authenticationFilter) throws Exception {
        authenticationFilter.setFilterProcessesUrl("/authentication");
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
                    requests.requestMatchers(HttpMethod.POST, "/user/files/upload").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/tags").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/socialMedia").permitAll();
                    requests.requestMatchers(AUTH_WHITELIST).permitAll();
                    requests.requestMatchers(HttpMethod.POST, "/authentication").permitAll();
                    requests.anyRequest().authenticated();
                })
                .addFilterBefore(new ExceptionHandlingFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(new JWTokenService()), AuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager, JWTokenService jwtTokenService) {
        return new AuthenticationFilter(authenticationManager, jwtTokenService);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
                .info(new Info()
                .title("Haunted Place API")
                .description("API Rest da aplicação Haunted Place")
                .contact(new Contact()
                .name("Developer")
                .email("victordev8@gmail.com")));
    }
}
