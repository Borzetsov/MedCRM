package by.tms.medcrm.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
}
