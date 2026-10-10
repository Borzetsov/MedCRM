package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.AdministratorDTO;
import by.tms.medcrm.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/administrators")
public class AdminAdministratorsController {

    private final AdministratorService administratorService;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("administrators", administratorService.findAll());
        return "administration/administrators/list";
    }

    @PostMapping
    public String create(AdministratorDTO administratorDTO, Model model) {
        administratorService.create(administratorDTO);
        model.addAttribute("administrators", administratorService.findAll());
        return "administration/administrators/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        administratorService.delete(id);
        model.addAttribute("administrators", administratorService.findAll());
        return "administration/administrators/list";
    }
}
