package uz.pdp.cambridgelc.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NotBlank(message = "Product title cannot be blank!")
    private String title;
    private Integer price;
    private String type;
}
