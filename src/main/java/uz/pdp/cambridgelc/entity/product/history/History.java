package uz.pdp.cambridgelc.entity.product.history;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.cambridgelc.entity.BaseEntity;
import uz.pdp.cambridgelc.entity.product.ProductEntity;

import java.time.LocalDateTime;

@Entity(name = "mini_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class History extends BaseEntity {
    private LocalDateTime boughtTime;
    @ManyToOne
    private ProductEntity product;
}
