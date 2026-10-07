package by.tms.medcrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "appointments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk1_appointments_patient_timeslot",
                        columnNames = {"patient", "appointment_date", "timeslot"}
                ),
                @UniqueConstraint(
                        name = "uk2_appointments_professional_timeslot",
                        columnNames = {"medical_professional", "appointment_date", "timeslot"}
                )
        }
)
@Getter
@Setter
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient")
    private Patient patient;

    @Column(name = "medical_service", nullable = false)
    private Long medicalServiceId;

    @Column(name = "medical_professional", nullable = false)
    private Long medicalProfessionalId;

    @Column(name = "medical_professional_category", nullable = false)
    private Long medicalProfessionalCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "medical_service", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "medical_professional_category",
                    referencedColumnName = "required_medical_professional_category", insertable = false, updatable = false)
    })
    private MedicalService medicalService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "medical_professional", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "medical_professional_category",
                    referencedColumnName = "category", insertable = false, updatable = false)
    })
    private MedicalProfessional medicalProfessional;


    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "timeslot")
    @Min(value = 1)
    @Max(value = 24)
    private Integer timeslot;

    @Column(name = "price_fact")
    private BigDecimal priceFact;

    @Column(name = "status")
    private AppointmentStatusType status;
}
