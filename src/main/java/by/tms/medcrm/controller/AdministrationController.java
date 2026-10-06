package by.tms.medcrm.controller;

import by.tms.medcrm.dto.MedicalProfessionalCategoryDTO;
import by.tms.medcrm.dto.MedicalServiceCategoryDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.repository.MedicalProfessionalCategoryRepository;
import by.tms.medcrm.repository.MedicalServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

    @Autowired
    MedicalServiceCategoryRepository medicalServiceCategoryRepository;

    @Autowired
    MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    @GetMapping("/medical_service_categories")
    public String medical_service_categories() {
        return "administration/medical_service_categories";
    }

    @PostMapping("/medical_service_categories")
    public String medical_service_categories(MedicalServiceCategoryDTO medicalServiceCategoryDTO) {
        MedicalServiceCategory medicalServiceCategory = new MedicalServiceCategory();
        medicalServiceCategory.setName(medicalServiceCategoryDTO.getName());
        medicalServiceCategoryRepository.save(medicalServiceCategory);
        return "administration/medical_service_categories";
    }

    @GetMapping("/medical_professionals_categories")
    public String medical_professionals_categories() {
        return "administration/medical_professionals_categories";
    }

    @PostMapping("/medical_professionals_categories")
    public String medical_professionals_categories(MedicalProfessionalCategoryDTO medicalProfessionalCategoryDTO) {
        MedicalProfessionalCategory medicalProfessionalCategory = new MedicalProfessionalCategory();
        medicalProfessionalCategory.setName(medicalProfessionalCategoryDTO.getName());
        medicalProfessionalCategoryRepository.save(medicalProfessionalCategory);
        return "administration/medical_professionals_categories";
    }
}
