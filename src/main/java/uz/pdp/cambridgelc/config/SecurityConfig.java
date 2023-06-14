package uz.pdp.cambridgelc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.cambridgelc.filter.JwtFilterToken;
import uz.pdp.cambridgelc.service.authUser.AuthService;
import uz.pdp.cambridgelc.service.authUser.AuthenticationService;
import uz.pdp.cambridgelc.service.authUser.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;
    private final AuthService authService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    String[] adminOnly = new String[]{"/api/v1/user/addStudent","/api/v1/user/addTeacher","/api/v1/user/addSupport"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/**","/api/v1/user/**").permitAll()
                        .requestMatchers(adminOnly).hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers("/api/v1/auth/addAdmin").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilterToken(authenticationService,jwtService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}
