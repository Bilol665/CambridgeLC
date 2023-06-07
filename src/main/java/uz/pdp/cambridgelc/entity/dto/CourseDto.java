package uz.pdp.cambridgelc.entity.dto;



import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseDto {
    private String title;
    private Integer duration;
    private Double price;
    private String level;
}
