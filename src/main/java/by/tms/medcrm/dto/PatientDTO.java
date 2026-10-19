package by.tms.medcrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    @NotBlank
    @NotNull
    @Pattern(regexp = "^[^0-9]*$", message = "Имя не должно содержать цифры")
    private String firstName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[^0-9]*$", message = "Фамилия не должна содержать цифры")
    private String lastName;

    @NotNull
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    @NotBlank
    @NotNull
    private String gender;

    @NotBlank
    @NotNull
    @Pattern(
            regexp = "^\\+\\d{11,14}$",
            message = "Номер должен начинаться с + и содержать от 11 до 14 цифр (например, +375291234567)"
    )
    private String phone;

    @NotBlank
    @NotNull
    private String email;
}
