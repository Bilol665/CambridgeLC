package uz.pdp.cambridgelc.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.course.CourseEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    void removeCourseEntityByTitle(@NotBlank String title);
    Optional<CourseEntity> findByTitle(String title);
}
