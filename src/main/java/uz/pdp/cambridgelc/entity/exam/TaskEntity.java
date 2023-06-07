package uz.pdp.cambridgelc.entity.exam;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;

@Entity(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TaskEntity extends BaseEntity {
    @NotBlank(message = "Task title cannot be blank!")
    private String title;
    private Integer reward;
    private String description;
    private String answer;
    @Enumerated(EnumType.STRING)
    private TaskDifficulty difficulty;
}
