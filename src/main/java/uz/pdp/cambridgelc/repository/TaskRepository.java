package uz.pdp.cambridgelc.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.exam.TaskEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    Optional<TaskEntity> findTaskEntityByTitle(@NotBlank(message = "Task title cannot be blank!") String title);
    Optional<TaskEntity> deleteTaskEntityById(UUID id);
}
