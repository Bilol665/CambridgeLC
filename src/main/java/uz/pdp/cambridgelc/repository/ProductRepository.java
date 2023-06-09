package uz.pdp.cambridgelc.repository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.product.ProductEntity;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update products set title = :title where id = :id")
    void update(@Param("title")String title,@Param("id")UUID id);
}
