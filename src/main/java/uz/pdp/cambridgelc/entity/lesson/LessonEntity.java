package uz.pdp.cambridgelc.entity.lesson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;

import java.time.Duration;

@Entity(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LessonEntity extends BaseEntity {
    @Column(unique = true)
    private String theme;
    private Duration duration;
    @Column(unique = true)
    private Integer sequenceNumber;
}
