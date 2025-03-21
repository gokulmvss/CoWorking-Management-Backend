package com.example.demo.config;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

import com.example.demo.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final CustomAuthenticationProvider authenticationProvider;
    @Autowired
	public SecurityConfig(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;        
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200"); // Allow your frontend origin
        configuration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.setAllowCredentials(true); // Allow cookies or authentication headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all endpoints
        return source;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/auth/register/**").permitAll()
                .requestMatchers("/api/complaints/**").permitAll()
//                .requestMatchers("/api/public/**").permitAll()
//                .requestMatchers("/api/space-owners/**").hasRole("SPACE_OWNER")
//                .requestMatchers("/api/companies/**").hasRole("COMPANY_ADMIN")
//                .requestMatchers("/api/employees/**").hasAnyRole("EMPLOYEE", "COMPANY_ADMIN")
//                .requestMatchers("/api/coworking-spaces/**").hasRole("SPACE_OWNER")
//                .requestMatchers("/api/workspaces/**").hasRole("SPACE_OWNER")
//                .requestMatchers("/api/seats/workspace/**").hasRole("SPACE_OWNER")
//                .requestMatchers("/api/seats/company/**").hasRole("COMPANY_ADMIN")
//                .requestMatchers("/api/bookings/**").hasAnyRole("EMPLOYEE", "COMPANY_ADMIN")
//                .anyRequest().authenticated()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/space-owners/**").permitAll()
                .requestMatchers("/api/companies/**").permitAll()
                .requestMatchers("/api/employees/**").permitAll()
                .requestMatchers("/api/coworking-spaces/**").permitAll()
                .requestMatchers("/api/workspaces/**").permitAll()
                .requestMatchers("/api/seats/workspace/**").permitAll()
                .requestMatchers("/api/seats/company/**").permitAll()
                .requestMatchers("/api/bookings/**").permitAll()
                .anyRequest().authenticated()
            )
//            .formLogin(form -> form
//                .loginPage("/api/auth/login")
//                .loginProcessingUrl("/api/auth/login")
//                .defaultSuccessUrl("/api/auth/login-success")
//                .failureUrl("/api/auth/login-failure")
//                .permitAll()
//            )
         // Replace form login with this:
            .formLogin(form -> form.disable())
            .httpBasic(basic -> {})
            // Add exception handling
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\":false,\"message\":\"Authentication required\",\"data\":null}");
                })
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                .logoutSuccessUrl("/api/auth/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/api/auth/session-expired")
            );
        
        return http.build();
    }

	@Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
