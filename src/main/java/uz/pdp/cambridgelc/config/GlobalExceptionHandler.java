package uz.pdp.cambridgelc.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.FailedAuthorizeException;
import uz.pdp.cambridgelc.exceptions.GroupNotFoundException;
import uz.pdp.cambridgelc.exceptions.CourseNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {CourseNotFoundException.class})
    public ResponseEntity<String> authException(CourseNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {GroupNotFoundException.class})
    public ResponseEntity<String> groupNotFoundException(GroupNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {FailedAuthorizeException.class})
    public ResponseEntity<String> failedAuthorize(FailedAuthorizeException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }
}