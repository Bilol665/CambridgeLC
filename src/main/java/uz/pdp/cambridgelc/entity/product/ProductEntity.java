package uz.pdp.cambridgelc.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {

    private String title;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private ProductType category;
}
