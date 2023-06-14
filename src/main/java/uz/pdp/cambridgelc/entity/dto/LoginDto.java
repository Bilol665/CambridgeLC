package uz.pdp.cambridgelc.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "Username can not be empty")
    private String username;
    @NotBlank(message = "Password can not be empty")
    private String password;
}
