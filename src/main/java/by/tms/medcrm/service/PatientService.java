package by.tms.medcrm.service;

import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final AccountService accountService;

    public Patient loadPatient(Long id) throws EntityNotFoundException {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пациент не найдет"));
    }

    public Patient findByLogin(String login) throws EntityNotFoundException {
        Account account = accountService.findByLogin(login);
        return patientRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пациент не найден"));
    }

    public Patient update(Patient patient) throws EntityNotFoundException {
        return patientRepository.save(patient);
    }
}
