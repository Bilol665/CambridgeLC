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
public class GroupCreateDto {
    @NotBlank(message = "Name Cannot Be Blank")
    private String name;
    @NotBlank(message = "Title Cannot Be Blank")
    private String courseTitle;
    @NotBlank(message = "Username Cannot Be Blank")
    private String teacherUsername;
}
