package by.tms.medcrm.controller;

import by.tms.medcrm.dto.MedicalProfessionalDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.AccountType;
import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.repository.MedicalProfessionalCategoryRepository;
import by.tms.medcrm.repository.MedicalProfessionalRepository;
import by.tms.medcrm.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/medical_professional")
public class MedicalProfessionalController {

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    private MedicalProfessionalRepository medicalProfessionalRepository;

    @Autowired
    private MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    @GetMapping("/registration")
    public String medicalProfessional() {
        return "account/medical_professional_registration";
    }

    @PostMapping("/registration")
    public String medicalProfessional(MedicalProfessionalDTO medicalProfessionalDTO) {
        MedicalProfessional medicalProfessional = new MedicalProfessional();

        Optional<MedicalProfessionalCategory> category = medicalProfessionalCategoryRepository
                .findByName(medicalProfessionalDTO.getCategory());
        if (category.isPresent()) {
            medicalProfessional.setCategory(category.get());
        }

        medicalProfessional.setFirstName(medicalProfessionalDTO.getFirstName());
        medicalProfessional.setLastName(medicalProfessionalDTO.getLastName());

        Account account = new Account();
        account.setType(AccountType.MEDICAL_PROFESSIONAL);
        account.setLogin("MD1");
        account.setPasswordHash(passwordUtil.hash("123"));

        medicalProfessional.setAccount(account);

        medicalProfessionalRepository.save(medicalProfessional);
        return "account/medical_professional_registration";
    }
}
