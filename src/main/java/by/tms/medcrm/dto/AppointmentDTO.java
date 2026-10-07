package by.tms.medcrm.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDTO {
    private String patientLastName;
    private String medicalService;
    private String medicalProfessionalLastName;
    private String medicalProfessionalCategory;
    private LocalDate appointmentDate;
    private Integer timeslot;
}
