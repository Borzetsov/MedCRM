package by.tms.medcrm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountDTO {

    @NotBlank
    @Length(min = 3, max = 20)
    private String login;

    @NotBlank
    @Length(min = 3, max = 20)
    private String password;
}
