package uz.pdp.cambridgelc.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreateDto {
    private String name;
    private CourseEntity course;
    private UserEntity teacher;
    private List<UserEntity> students;
}
