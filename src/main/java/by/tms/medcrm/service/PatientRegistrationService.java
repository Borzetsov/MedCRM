package by.tms.medcrm.service;

import by.tms.medcrm.dto.AccountDTO;
import by.tms.medcrm.dto.PatientDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.GenderType;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.entity.Role;
import by.tms.medcrm.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientRegistrationService {

    private final AccountService accountService;
    private final PatientRepository patientRepository;

    public Patient createPatient(PatientDTO patientDTO, AccountDTO accountDTO) {
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender().equals("MALE") ? GenderType.MALE : GenderType.FEMALE);
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());

        Account account = accountService.create(accountDTO, Role.ROLE_PATIENT);
        patient.setAccount(account);

        return patientRepository.save(patient);
    }
}
