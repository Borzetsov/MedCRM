package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.MedicalProfessionalDTO;
import by.tms.medcrm.entity.MedicalProfessionalId;
import by.tms.medcrm.service.MedicalProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/medical-professionals")
public class AdminMedicalProfessionalsController {

    private final MedicalProfessionalService medicalProfessionalService;

    @GetMapping()
    private String showAll(Model model) {
        model.addAttribute("medicalProfessionals", medicalProfessionalService.findAll());
        return "administration/medical-professionals/list";
    }

    @PostMapping()
    private String create(MedicalProfessionalDTO medicalProfessionalDTO, Model model) {
        medicalProfessionalService.create(medicalProfessionalDTO);
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
