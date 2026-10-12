package by.tms.medcrm.controller.administration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administration")
public class AdministrationController {

    @GetMapping
    public String showDashboard(Model model) {
        return "administration/dashboard";
    }
}
