package uz.pdp.cambridgelc.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.security.Principal;
import java.util.Objects;

@Service
public class MailSenderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private String verifyCode;

    public void generateNVerify(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String message = String.valueOf(Math.random());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(userEntity.getEmail());
        javaMailSender.send(simpleMailMessage);
        this.verifyCode = message;
    }

    public Boolean confirmVerifyCode(String verifyCode, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(Objects.equals(verifyCode, this.verifyCode))
            userEntity.setIsVerified(true);
        return true;
    }
}
