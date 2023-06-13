package uz.pdp.cambridgelc.entity.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;

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

}
