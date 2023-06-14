package uz.pdp.cambridgelc.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LessonDto {
    private String theme;
    private Integer sequenceNumber;
    private Long duration;
}
