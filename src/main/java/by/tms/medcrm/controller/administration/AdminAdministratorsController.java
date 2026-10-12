package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.AdministratorDTO;
import by.tms.medcrm.entity.Administrator;
import by.tms.medcrm.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("administrator", administratorService.findById(id));
        return "administration/administrators/edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("administrator") Administrator administrator, Model model) {
        administratorService.update(administrator);
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
