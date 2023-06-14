package uz.pdp.cambridgelc.service.exam;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.ExamDto;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.repository.ExamRepository;
import uz.pdp.cambridgelc.repository.GroupRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamService {
    private final ExamRepository examRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public ExamEntity add(ExamDto examDto) {
        ExamEntity exam = modelMapper.map(examDto, ExamEntity.class);
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(examDto.getGroupTitle()).orElseThrow(
                () -> new DataNotFoundException("Group not found!")
        );
        exam.setGroup(groupEntity);
        return examRepository.save(exam);
    }

    public Boolean start(UUID examId) {
//        ExamEntity examEntity = examRepository.findById(examId).orElseThrow(
//                () -> new DataNotFoundException("Exam not found!")
//        );
//        examEntity.setStarted(true);
//        examRepository.save(examEntity);
        examRepository.updateExamEntityById(examId,true);
        return true;
    }
}
