package uz.pdp.cambridgelc.entity.dto;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseDto {
    @NotBlank(message = "Title can not be omitted ")
    private String title;
    @NotNull(message = "Duration can not be omitted")
    private Integer duration;
    @NotNull(message = "Price must be told")
    private Double price;
    @NotBlank(message = "Level !!!")
    private String level;
}
