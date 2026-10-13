package by.tms.medcrm.dto;

import by.tms.medcrm.entity.MedicalProfessionalCategory;
import lombok.Data;

@Data
public class MedicalServiceCategoryDTO {
    private String name;
    private MedicalProfessionalCategory defaultProfessionalCategory;
}
