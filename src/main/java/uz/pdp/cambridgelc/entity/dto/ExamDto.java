package uz.pdp.cambridgelc.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExamDto {
    @NotBlank(message = "Group title cannot be empty")
    private String groupTitle;
    private Double acceptableScore;
    private Integer reward;
}
