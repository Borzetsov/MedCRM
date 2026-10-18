package by.tms.medcrm.service;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.*;
import by.tms.medcrm.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalProfessionalRepository medicalProfessionalRepository;
    private final MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;
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

    public void cancel(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> findAllByPatient(Patient patient) {
        return appointmentRepository.findAllByPatientId(patient.getId());
    }

    public List<Appointment> findAllByMedicalProfessionalIdAndAppointmentDate(MedicalProfessional medicalProfessional, LocalDate date) {
        return appointmentRepository.findAllByMedicalProfessionalIdAndAppointmentDate(medicalProfessional.getId().getId(), date);
    }

    public Set<Integer> getAvailableTimeslotsOnDate(MedicalService service, LocalDate date) {
        Set<Integer> availableTimeslots = new HashSet<>();
        List<MedicalProfessional> professionals = medicalProfessionalService
                .findAllByCategory(service.getRequiredMedicalProfessionalCategory());
        for (MedicalProfessional medicalProfessional : professionals) {
            Set<Integer> timeslots = getAvailableTimeslotsByMedicalProfessionalOnDate(medicalProfessional, date);
            availableTimeslots.addAll(timeslots);
        }
        return availableTimeslots;
    }

    public Set<Integer> getAvailableTimeslotsByMedicalProfessionalOnDate(MedicalProfessional medicalProfessional, LocalDate date) throws EntityNotFoundException {
        MedicalProfessionalId medicalProfessionalId = medicalProfessional.getId();
        MedicalProfessional managedProfessional = medicalProfessionalRepository.findById(medicalProfessionalId)
                .orElseThrow(() -> new EntityNotFoundException("Медицинский работник не найден"));
        List<Appointment> bookedAppointments = findAllByMedicalProfessionalIdAndAppointmentDate(managedProfessional, date);
        Set<Integer> availableTimeslots = new HashSet<>();
        for (int i = 1; i <= 24; i++) availableTimeslots.add(i);
        for (Appointment appointment : bookedAppointments) availableTimeslots.remove(appointment.getTimeslot());
        return availableTimeslots;
    }

    public Optional<MedicalProfessional> findProfessional(MedicalProfessionalCategory category,
                                                LocalDate appointmentDate,
                                                Integer timeslot) {
        List<MedicalProfessional> professionals = medicalProfessionalService.findAllByCategory(category);
        for (MedicalProfessional medicalProfessional : professionals) {
            if (!appointmentRepository
                    .existsByMedicalProfessionalIdAndAppointmentDateAndTimeslot(medicalProfessional.getId().getId(),
                            appointmentDate, timeslot)) {
                return Optional.of(medicalProfessional);
            }
        }
        return Optional.empty();
    }
}
