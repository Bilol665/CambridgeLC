package uz.pdp.cambridgelc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, UUID> {
    @Modifying
    @Query("update exams e set e.started = :status where e.id = :id")
    void updateStartedStatusById(@Param("status") boolean status, @Param("id") UUID id);
}
