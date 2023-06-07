package uz.pdp.cambridgelc.entity.exam;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.Map;

@Entity(name = "exams")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExamEntity extends BaseEntity {
    @ManyToOne
    private GroupEntity group;
    private Double acceptableScore;
    private Integer reward;
}
