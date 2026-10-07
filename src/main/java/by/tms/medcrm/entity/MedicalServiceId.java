package by.tms.medcrm.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class MedicalServiceId implements Serializable {
    private Long id;
    private Long requiredMedicalProfessionalCategory;
}
