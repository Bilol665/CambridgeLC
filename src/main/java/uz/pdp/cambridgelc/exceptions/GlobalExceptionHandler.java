package uz.pdp.cambridgelc.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {RequestValidationException.class})
    private ResponseEntity<String>RequestValidationException(RequestValidationException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
