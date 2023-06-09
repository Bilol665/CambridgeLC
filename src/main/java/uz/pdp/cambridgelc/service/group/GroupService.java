package uz.pdp.cambridgelc.service.group;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.GroupNotFoundException;
import uz.pdp.cambridgelc.repository.CourseRepository;
import uz.pdp.cambridgelc.repository.GroupRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public GroupEntity save(GroupCreateDto groupCreateDto){
        UserEntity teacher = userRepository.findUserEntityByUsername(groupCreateDto.getTeacherUsername())
                .orElseThrow(() -> new DataNotFoundException("Teacher Not found"));
        CourseEntity course = courseRepository.findByTitle(groupCreateDto.getCourseTitle())
                .orElseThrow(() -> new DataNotFoundException("Course Not Found"));

        GroupEntity group = modelMapper.map(groupCreateDto, GroupEntity.class);
        group.setTeacher(teacher);
        group.setCourse(course);
        return groupRepository.save(group);
    }

    public GroupEntity addStudent(UUID groupId,String studentUsername){
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found!")
        );
        UserEntity userEntity = userRepository.findUserEntityByUsername(studentUsername)
                .orElseThrow(() -> new DataNotFoundException("Student Not Found"));

        userEntity.setGroup(groupEntity);
        return userRepository.save(userEntity).getGroup();
    }

    public GroupEntity getGroup(UUID groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));
    }

    public GroupEntity updateGroupName(UUID groupId, String name){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        group.setName(name);
        return groupRepository.save(group);
    }

    public GroupEntity updateTeacher(UUID groupId, String teacherUsername){
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));

        UserEntity teacher = userRepository.findUserEntityByUsername(teacherUsername)
                .orElseThrow(() -> new DataNotFoundException("Teacher Not Found"));
        group.setTeacher(teacher);
        return groupRepository.save(group);
    }

//    public GroupEntity updateGroupName(UUID groupId, List<UserEntity> failedStudents){
//        GroupEntity group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new GroupNotFoundException("Group Not Found"));
//
//        group.setFailedStudents(failedStudents);
//        return  groupRepository.save(group);
//    }

    public List<GroupEntity> getAllGroups(int page,int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        return groupRepository.findAll(pageable).getContent();
    }
}
