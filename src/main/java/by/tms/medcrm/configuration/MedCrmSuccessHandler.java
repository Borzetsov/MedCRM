package by.tms.medcrm.configuration;

import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MedCrmSuccessHandler implements AuthenticationSuccessHandler {

    private final PatientService patientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMINISTRATOR_CRM")) {
            response.sendRedirect("/administration");
        } else if (roles.contains("ROLE_PATIENT")) {
            response.sendRedirect("/patients/profile");
        } else {
            response.sendRedirect("/auth/login");
        }
    }
}
