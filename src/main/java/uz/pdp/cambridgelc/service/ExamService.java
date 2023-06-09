package uz.pdp.cambridgelc.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.repository.ExamRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamService {
    private final ExamRepository examrepository;

}
