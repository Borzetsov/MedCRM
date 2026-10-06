package by.tms.medcrm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/account/registration", "/account/registration/**").permitAll()
                        .requestMatchers("/administration/medical_service_categories", "/administration/medical_service_categories/**").permitAll()
                        .requestMatchers("/administration/medical_professionals_categories", "/administration/medical_professionals_categories/**").permitAll()
                        .requestMatchers("/patient/registration", "/patient/registration/**").permitAll()
                        .requestMatchers("/medical_professional/registration", "/medical_professional/registration/**").permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
