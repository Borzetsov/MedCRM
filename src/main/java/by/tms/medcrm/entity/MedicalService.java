package by.tms.medcrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;

@Entity
@Table(name = "medical_services")
@IdClass(MedicalServiceId.class)
@Getter
@Setter
@ToString
public class MedicalService implements Persistable<MedicalServiceId> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private MedicalServiceCategory category;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "required_medical_professional_category")
    private MedicalProfessionalCategory requiredMedicalProfessionalCategory;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Override
    public MedicalServiceId getId() {
        return new MedicalServiceId(this.id,
                this.requiredMedicalProfessionalCategory != null
                        ? this.requiredMedicalProfessionalCategory.getId() : null);
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.id == null;
    }
}
