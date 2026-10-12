package by.tms.medcrm.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MedCrmSuccessHandler medCrmSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/login", "/auth/login-error").permitAll()
                        .requestMatchers("/patients/registration", "/patients/registration/**").permitAll()
                        .requestMatchers("/patients/main", "/patients/main/**").hasRole("PATIENT")
                        .requestMatchers("/appointment/registration", "/appointment/registration/**").hasAnyRole("PATIENT", "ADMINISTRATOR")
                        .requestMatchers("/administration", "/administration/**").hasRole("ADMINISTRATOR_CRM")
                        .requestMatchers("/administration/medical-services/categories", "/administration/medical-services/categories/**").hasRole("ADMINISTRATOR_CRM")
                        .requestMatchers("/administration/medical-professionals/categories", "/administration/medical-professionals/categories/**").hasRole("ADMINISTRATOR_CRM")
                        .requestMatchers("/administration/medical-professionals", "/administration/medical-professionals/**").hasRole("ADMINISTRATOR_CRM")
                        .requestMatchers("/administration/medical-services", "/administration/medical-services/**").hasRole("ADMINISTRATOR_CRM")
                        .requestMatchers("/administration/administrators", "/administration/administrators/**").hasRole("ADMINISTRATOR_CRM")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .failureUrl("/auth/login-error?error=true")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(medCrmSuccessHandler)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
