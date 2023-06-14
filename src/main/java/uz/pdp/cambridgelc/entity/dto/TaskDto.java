package uz.pdp.cambridgelc.entity.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.cambridgelc.entity.exam.TaskDifficulty;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaskDto {
    @NotBlank(message = "Task title cannot be blank!")
    private String title;
    private Integer reward;
    @NotBlank(message = "Description can not empty")
    private String description;
    @NotBlank(message = "Answer can not empty")
    private String answer;
    private String difficulty;
}
