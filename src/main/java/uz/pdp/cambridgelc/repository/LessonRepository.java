package uz.pdp.cambridgelc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cambridgelc.entity.lesson.LessonEntity;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity,UUID> {
    List<LessonEntity> findLessonEntitiesByTheme(String theme);
}
