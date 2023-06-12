package uz.pdp.cambridgelc.service.group;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
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

    public GroupEntity save(BindingResult bindingResult,GroupCreateDto groupCreateDto){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        UserEntity teacher = userRepository.findUserEntityByUsername(groupCreateDto.getTeacherUsername())
                .orElseThrow(() -> new DataNotFoundException("Teacher Not found"));
        CourseEntity course = courseRepository.findByTitle(groupCreateDto.getCourseTitle())
                .orElseThrow(() -> new DataNotFoundException("Course Not Found"));

        GroupEntity group = modelMapper.map(groupCreateDto, GroupEntity.class);
        group.setTeacher(teacher);
        group.setCourse(course);
        return groupRepository.save(group);
    }

    public GroupEntity getGroup(BindingResult bindingResult,UUID groupId){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("Group Not Found"));
    }

    public GroupEntity updateGroupName(BindingResult bindingResult,UUID groupId, String name){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw  new RequestValidationException(errors);
        }
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("Group Not Found"));

        group.setName(name);
        return groupRepository.save(group);
    }

    public GroupEntity updateTeacher(BindingResult bindingResult,UUID groupId, String teacherUsername){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw  new RequestValidationException(errors);
        }
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("Group Not Found"));

        UserEntity teacher = userRepository.findUserEntityByUsername(teacherUsername)
                .orElseThrow(() -> new DataNotFoundException("Teacher Not Found"));
        group.setTeacher(teacher);
        return groupRepository.save(group);
    }


    public List<GroupEntity> getAllGroups(BindingResult bindingResult,int page,int size){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw  new RequestValidationException(errors);
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        return groupRepository.findAll(pageable).getContent();
    }
    public List<GroupEntity> getGroupsByTeacher(BindingResult bindingResult,String teacherUsername) {
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw  new RequestValidationException(errors);
        }
        return groupRepository.findByTeacher(userRepository.findUserEntityByUsername(teacherUsername).orElseThrow(
                () -> new DataNotFoundException("Teacher not found!")
        ));
    }
    @Transactional
    public void deleteGroupByName(BindingResult bindingResult,String name){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        groupRepository.deleteGroupEntityByName(name)
                .orElseThrow(()-> new DataNotFoundException("Group not found"));
    }
}
