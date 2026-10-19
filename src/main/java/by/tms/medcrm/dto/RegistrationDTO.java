package by.tms.medcrm.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class RegistrationDTO {
    @Valid
    private AccountDTO newAccountDTO;

    @Valid
    private PatientDTO newPatientDTO;
}
