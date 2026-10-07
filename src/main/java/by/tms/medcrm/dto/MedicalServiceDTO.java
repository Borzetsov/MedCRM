package by.tms.medcrm.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicalServiceDTO {
    private String name;
    private BigDecimal price;
    private String category;
    private String requiredMedicalProfessionalCategory;
}
