package uz.pdp.cambridgelc.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    @NotBlank(message = "Product title cannot be blank!")
    private String title;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private ProductType category;
}
