package uz.pdp.cambridgelc.entity.exam;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.List;
import java.util.Map;

@Entity(name = "exams")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExamEntity extends BaseEntity {
    private String title;
    @ManyToOne
    private GroupEntity group;
    private Double acceptableScore;
    private Integer reward;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TaskEntity> tasks;
    private Boolean started;
}
