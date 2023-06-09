package uz.pdp.cambridgelc.service;

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

@Service
@RequiredArgsConstructor
@Transactional
public class ExamService {
    private final ExamRepository examrepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public ExamEntity add(ExamDto examDto) {
        ExamEntity exam = modelMapper.map(examDto, ExamEntity.class);
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(examDto.getGroupTitle()).orElseThrow(
                () -> new DataNotFoundException("Group not found!")
        );
        exam.setGroup(groupEntity);
        return examrepository.save(exam);
    }

}
