package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.AccountDTO;
import by.tms.medcrm.dto.MedicalProfessionalDTO;
import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalProfessionalId;
import by.tms.medcrm.service.MedicalProfessionalCategoryService;
import by.tms.medcrm.service.MedicalProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/medical-professionals")
public class AdminMedicalProfessionalsController {

    private final MedicalProfessionalService medicalProfessionalService;
    private final MedicalProfessionalCategoryService medicalProfessionalCategoryService;

    @GetMapping()
    private String showAll(Model model) {
        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        model.addAttribute("medicalProfessionals", medicalProfessionalService.findAll());
        return "administration/medical-professionals/list";
    }

    @PostMapping()
    private String create(MedicalProfessionalDTO medicalProfessionalDTO,
                          AccountDTO accountDTO, Model model) {
        medicalProfessionalService.create(medicalProfessionalDTO, accountDTO);
        model.addAttribute("medicalProfessionals", medicalProfessionalService.findAll());
        return "administration/medical-professionals/list";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam(name = "professionalId") Long professionalId,
                                 @RequestParam(name = "categoryId") Long categoryId,
                                 Model model) {
        MedicalProfessionalId medicalProfessionalId = new MedicalProfessionalId();
        medicalProfessionalId.setId(professionalId);
        medicalProfessionalId.setCategory(categoryId);

        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        model.addAttribute("medicalProfessional", medicalProfessionalService.findById(medicalProfessionalId));
        model.addAttribute("currentProfessionalId", professionalId);
        model.addAttribute("currentCategoryId", categoryId);
        return "administration/medical-professionals/edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("medicalProfessional") MedicalProfessional medicalProfessional,
                                 @RequestParam(name = "professionalId") Long professionalId,
                                 @RequestParam(name = "categoryId") Long categoryId,
                                 Model model) {
        medicalProfessional.setId(professionalId);
        MedicalProfessionalCategory category = new MedicalProfessionalCategory();
        category.setId(categoryId);
        medicalProfessional.setCategory(category);
        medicalProfessionalService.update(medicalProfessional);

        model.addAttribute("medicalProfessionals", medicalProfessionalService.findAll());
        return "administration/medical-professionals/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "categoryId") Long categoryId,
                         Model model) {
        MedicalProfessionalId medicalProfessionalId = new MedicalProfessionalId();
        medicalProfessionalId.setId(id);
        medicalProfessionalId.setCategory(categoryId);
        medicalProfessionalService.delete(medicalProfessionalId);
        model.addAttribute("medicalProfessionals", medicalProfessionalService.findAll());
        return "administration/medical-professionals/list";
    }
}
