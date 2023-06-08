package uz.pdp.cambridgelc.service.group;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.GroupNotFoundException;
import uz.pdp.cambridgelc.repository.GroupRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupEntity save(GroupCreateDto groupCreateDto){
        GroupEntity group = modelMapper.map(groupCreateDto, GroupEntity.class);
        return groupRepository.save(group);
    }

    public GroupEntity addStudent(UUID groupId,UserEntity student){
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found!")
        );
        List<UserEntity> students = groupEntity.getStudents();
        students.add(student);
        groupEntity.setStudents(students);
        return groupRepository.save(groupEntity);
    }

    public GroupEntity getGroup(UUID groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));
    }

    public GroupEntity update(UUID groupId,String name){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setName(name);
        return groupRepository.save(group);
    }

    public GroupEntity update(UUID groupId, UserEntity newTeacher){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setTeacher(newTeacher);
        return groupRepository.save(group);
    }

    public GroupEntity update(UUID groupId, List<UserEntity> failedStudents){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setFailedStudents(failedStudents);
        return  groupRepository.save(group);
    }

    public List<GroupEntity> getAllGroups(int page,int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        return groupRepository.findAll(pageable).getContent();
    }
}
