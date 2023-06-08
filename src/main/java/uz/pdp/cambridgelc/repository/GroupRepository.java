package uz.pdp.cambridgelc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
    GroupEntity updateGroupEntityById (UUID groupId, UserEntity student);
}
