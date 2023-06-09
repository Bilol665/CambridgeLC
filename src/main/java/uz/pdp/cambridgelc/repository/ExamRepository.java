package uz.pdp.cambridgelc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;

import java.util.UUID;

public interface ExamRepository extends JpaRepository<ExamEntity, UUID> {
}
