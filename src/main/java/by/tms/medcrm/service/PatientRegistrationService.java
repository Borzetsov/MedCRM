package by.tms.medcrm.service;

import by.tms.medcrm.dto.RegistrationDTO;
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

    public Patient createPatient(RegistrationDTO registrationDTO) {
        Patient patient = new Patient();
        patient.setFirstName(registrationDTO.getNewPatientDTO().getFirstName());
        patient.setLastName(registrationDTO.getNewPatientDTO().getLastName());
        patient.setBirthDate(registrationDTO.getNewPatientDTO().getBirthDate());
        patient.setGender(registrationDTO.getNewPatientDTO().getGender().equals("MALE") ? GenderType.MALE : GenderType.FEMALE);
        patient.setPhone(registrationDTO.getNewPatientDTO().getPhone());
        patient.setEmail(registrationDTO.getNewPatientDTO().getEmail());

        Account account = accountService.create(registrationDTO.getNewAccountDTO(), Role.ROLE_PATIENT);
        patient.setAccount(account);

        return patientRepository.save(patient);
    }

    public Boolean existsByPhone(String phone) {
        return patientRepository.existsByPhone(phone);
    }

    public Boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }
}
