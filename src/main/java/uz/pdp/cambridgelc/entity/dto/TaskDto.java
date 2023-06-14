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
    @NotBlank(message = "Task description cannot be blank!")
    private String description;
    @NotBlank(message = "Task answer cannot be blank!")
    private String answer;
    @NotBlank(message = "Task difficulty cannot be blank!")
    private String difficulty;
}
