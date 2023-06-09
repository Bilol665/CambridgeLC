package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.LoginDto;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.dto.response.JwtResponse;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.entity.user.UserRole;
import uz.pdp.cambridgelc.entity.user.UserStatus;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.FailedAuthorizeException;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserEntity saveUser(UserCreateDto userDto, List<UserRole> role){
        UserEntity userEntity = modelMapper.map(userDto,UserEntity.class);
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
        throw new FailedAuthorizeException("User status unpaid please try again by making the status paid !");
    }
}
