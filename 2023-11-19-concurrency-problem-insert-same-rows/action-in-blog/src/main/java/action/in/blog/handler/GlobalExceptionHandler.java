package action.in.blog.handler;

import action.in.blog.exception.DuplicatedCollectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DuplicatedCollectException.class)
    public ResponseEntity<String> duplicatedCollectException(DuplicatedCollectException exception) {
        return ResponseEntity.status(600)
                .body(exception.getMessage());
    }
}
