package by.tms.medcrm.service;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.*;
import by.tms.medcrm.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalProfessionalRepository medicalProfessionalRepository;
    private final MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    private final PatientService patientService;
    private final MedicalServiceService medicalServiceService;
    private final MedicalProfessionalService medicalProfessionalService;

    public Appointment book(@Valid AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();

        Optional<Patient> patient = patientRepository.findByLastName(appointmentDTO.getPatientLastName());
        if (patient.isPresent()) {
            appointment.setPatient(patient.get());
        }

        Optional<MedicalService> medicalService = medicalServiceRepository
                .findByName(appointmentDTO.getMedicalService());
        if (medicalService.isPresent()) {
            appointment.setMedicalService(medicalService.get());
            appointment.setMedicalServiceId(appointment.getMedicalService().getId().getId());
            appointment.setPriceFact(medicalService.get().getPrice());
        }

        Optional<MedicalProfessional> medicalProfessional = medicalProfessionalRepository
                .findByLastName(appointmentDTO.getMedicalProfessionalLastName());
        if (medicalProfessional.isPresent()) {
            appointment.setMedicalProfessional(medicalProfessional.get());
            appointment.setMedicalProfessionalId(appointment.getMedicalProfessional().getId().getId());
        }

        Optional<MedicalProfessionalCategory> medicalProfessionalCategory = medicalProfessionalCategoryRepository
                .findByName(appointmentDTO.getMedicalProfessionalCategory());
        if (medicalProfessionalCategory.isPresent()) {
            appointment.setMedicalProfessionalCategoryId(medicalProfessionalCategory.get().getId());
        }

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setTimeslot(appointmentDTO.getTimeslot());
        appointment.setStatus(AppointmentStatusType.SCHEDULED);
        return appointmentRepository.save(appointment);
    }

    public void cancel(Long  appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> findAllByPatient(Patient patient) {
        return appointmentRepository.findAllByPatientId(patient.getId());
    }
}
