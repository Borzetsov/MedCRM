package by.tms.medcrm.controller;

import by.tms.medcrm.dto.PatientDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.AccountType;
import by.tms.medcrm.entity.GenderType;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.repository.PatientRepository;
import by.tms.medcrm.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/registration")
    public String registration(){
        return "account/patient_registration";
    }

    @PostMapping("/registration")
    public String registration(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        if (patientDTO.getGender().equals("M")) {
            patient.setGender(GenderType.MALE);
        }
        else patient.setGender(GenderType.FEMALE);
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());

        Account account = new Account();
        account.setLogin(patientDTO.getEmail());
        account.setPasswordHash(passwordUtil.hash("12345678"));
        account.setType(AccountType.PATIENT);

        patient.setAccount(account);

        patientRepository.save(patient);
        return "account/patient_registration";
    }
}
