package uz.pdp.cambridgelc.entity.group;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;



@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupEntity extends BaseEntity {
    @NotBlank(message = "Group name cannot be empty!")
    private String name;
    @ManyToOne
    private CourseEntity course;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity teacher;

}
