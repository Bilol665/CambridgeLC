package uz.pdp.cambridgelc.entity.product.history;

import jakarta.persistence.*;
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
    @ManyToOne
    private UserEntity owner;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<History> miniHistory;
    private Integer total;
}
