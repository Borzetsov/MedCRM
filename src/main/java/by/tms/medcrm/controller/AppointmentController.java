package by.tms.medcrm.controller;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.*;
import by.tms.medcrm.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private MedicalProfessionalRepository medicalProfessionalRepository;

    @Autowired
    private MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    @GetMapping("/registration")
    public String appointmentRegistration(){
        return "appointment_registration";
    }

    @PostMapping("/registration")
    public String appointmentRegistration(@Valid AppointmentDTO appointmentDTO){
        Appointment appointment = new Appointment();

        Optional<Patient> patient = patientRepository.findByLastName(appointmentDTO.getPatientLastName());
        if (patient.isPresent()){
            appointment.setPatient(patient.get());
        }

        Optional<MedicalService> medicalService = medicalServiceRepository
                .findByName(appointmentDTO.getMedicalService());
        if (medicalService.isPresent()){
            appointment.setMedicalService(medicalService.get());
            appointment.setMedicalServiceId(appointment.getMedicalService().getId().getId());
            appointment.setPriceFact(medicalService.get().getPrice());
        }

        Optional<MedicalProfessional> medicalProfessional = medicalProfessionalRepository
                .findByLastName(appointmentDTO.getMedicalProfessionalLastName());
        if (medicalProfessional.isPresent()){
            appointment.setMedicalProfessional(medicalProfessional.get());
            appointment.setMedicalProfessionalId(appointment.getMedicalProfessional().getId().getId());
        }

        Optional<MedicalProfessionalCategory> medicalProfessionalCategory = medicalProfessionalCategoryRepository
                .findByName(appointmentDTO.getMedicalProfessionalCategory());
        if  (medicalProfessionalCategory.isPresent()){
            appointment.setMedicalProfessionalCategoryId(medicalProfessionalCategory.get().getId());
        }

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setTimeslot(appointmentDTO.getTimeslot());
        appointment.setStatus(AppointmentStatusType.SCHEDULED);
        appointmentRepository.save(appointment);
        return "appointment_registration";
    }
}
