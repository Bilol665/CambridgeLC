package uz.pdp.cambridgelc.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.cambridgelc.exceptions.NotCourseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotCourseException.class})
    public ResponseEntity<Object> authException(NotCourseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}