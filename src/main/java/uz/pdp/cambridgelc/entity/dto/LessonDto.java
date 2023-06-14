package uz.pdp.cambridgelc.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LessonDto {
    @NotBlank(message = "Theme can not be empty")
    @Column(unique = true)
    private String theme;
    @NotNull(message = "SequenceNumber can not be empty")
    @Column(unique = true)
    private Integer sequenceNumber;
    private Long duration;
}
