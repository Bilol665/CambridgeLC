package uz.pdp.cambridgelc.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreateDto {
    private String name;
    private CourseDto course;
    private UserCreateDto teacher;
}
