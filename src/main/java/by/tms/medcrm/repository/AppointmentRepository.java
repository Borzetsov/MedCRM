package by.tms.medcrm.repository;

import by.tms.medcrm.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public List<Appointment> findAllByPatientId(Long patientId);

    List<Appointment> findAllByMedicalProfessionalIdAndAppointmentDate(Long medicalProfessionalId, LocalDate appointmentDate);

    List<Appointment> findAllByMedicalProfessionalId(Long medicalProfessionalId);

    Boolean existsByMedicalProfessionalIdAndAppointmentDateAndTimeslot(Long medicalProfessionalId, LocalDate appointmentDate, Integer timeslot);
}
