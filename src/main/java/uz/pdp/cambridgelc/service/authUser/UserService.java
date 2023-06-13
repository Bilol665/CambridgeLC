package uz.pdp.cambridgelc.service.authUser;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.dto.LoginDto;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.dto.response.JwtResponse;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.entity.user.UserRole;
import uz.pdp.cambridgelc.entity.user.UserStatus;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.FailedAuthorizeException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
import uz.pdp.cambridgelc.repository.GroupRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    public UserEntity saveUser(UserCreateDto userDto, List<UserRole> role){

        UserEntity userEntity = modelMapper.map(userDto,UserEntity.class);
        switch (role.get(0)){
            case ROLE_ADMIN -> {
                GroupEntity group = groupRepository.findGroupEntityByName("ADMINS")
                        .orElseThrow(() -> new DataNotFoundException("Group Not Found"));
                userEntity.setGroup(group);
            }
            case ROLE_TEACHER -> {
                GroupEntity group = groupRepository.findGroupEntityByName("TEACHERS")
                        .orElseThrow(() -> new DataNotFoundException("Group Not Found"));
                userEntity.setGroup(group);
            }
            case ROLE_SUPPORT -> {
                GroupEntity group = groupRepository.findGroupEntityByName("SUPPORTS")
                        .orElseThrow(() -> new DataNotFoundException("Group Not Found"));
                userEntity.setGroup(group);
            }
            case ROLE_SUPER_ADMIN -> {
                GroupEntity group = groupRepository.findGroupEntityByName("SUPER_ADMINS")
                        .orElseThrow(() -> new DataNotFoundException("Group Not Found"));
                userEntity.setGroup(group);
            }
        }
        userEntity.setRoles(role);
        userEntity.setStatus(UserStatus.PAID);
        userEntity.setIsOut(false);
        userEntity.setCredits(0);
        userEntity.setSolvedTasks(0);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public JwtResponse login(LoginDto loginDto){
        UserEntity userEntity=userRepository.findUserEntityByUsername(loginDto.getUsername())
                .orElseThrow(()-> new DataNotFoundException("User not found"));
        if (passwordEncoder.matches(loginDto.getPassword(),userEntity.getPassword())){
            String accessToken=jwtService.generateAccessToken(userEntity);
            return JwtResponse.builder().accessToken(accessToken).build();
        }
        throw new FailedAuthorizeException("User status unpaid or password is incorrect !");
    }

    public GroupEntity addStudent(BindingResult bindingResult, UUID groupId, String studentUsername){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw  new RequestValidationException(errors);
        }
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found!")
        );
        UserEntity userEntity = userRepository.findUserEntityByUsername(studentUsername)
                .orElseThrow(() -> new DataNotFoundException("Student Not Found"));

        userEntity.setGroup(groupEntity);
        return userRepository.save(userEntity).getGroup();
    }

    public void updateCreditsById(UUID userId,Integer credits){
        UserEntity userEntity = userRepository
                .getUserEntityById(userId).orElseThrow(()->new DataNotFoundException("User not found"));
        userEntity.setCredits(credits);
        userRepository.save(userEntity);
    }
}
