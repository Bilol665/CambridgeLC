package uz.pdp.cambridgelc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.product.ProductHistoryEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<ProductHistoryEntity, UUID> {
    Optional<ProductHistoryEntity> findProductHistoryEntityByOwner(UserEntity owner);
}
