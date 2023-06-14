package uz.pdp.cambridgelc.entity.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.lesson.LessonEntity;

import java.util.List;

@Entity(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseEntity extends BaseEntity {
    @NotBlank(message = "Can not be omitted")
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private CourseLevel level;
    private Integer duration;
    private Double price;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<LessonEntity> lessons;
}
