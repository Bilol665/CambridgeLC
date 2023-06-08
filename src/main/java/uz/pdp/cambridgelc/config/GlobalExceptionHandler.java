package uz.pdp.cambridgelc.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.cambridgelc.exceptions.GroupNotFoundException;
import uz.pdp.cambridgelc.exceptions.NotCourseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotCourseException.class})
    public ResponseEntity<String> authException(NotCourseException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = {GroupNotFoundException.class})
    public ResponseEntity<String> groupNotFoundException(GroupNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

}