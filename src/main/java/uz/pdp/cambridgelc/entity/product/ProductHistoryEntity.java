package uz.pdp.cambridgelc.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.List;

@Entity(name = "histories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductHistoryEntity extends BaseEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProductEntity> products;
    private Integer total;
    @ManyToOne
    private UserEntity owner;
}
