package tasker.tasker.support;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tasker.tasker.exception.EntityNotFoundException;

@ControllerAdvice
public class TaskerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExcDto handleEntityNotFoundException(Exception e) {
        return new ExcDto(e);
    }

    @Data
    public static class ExcDto {
        private String message;

        ExcDto(Exception e) {
            this.message = e.getMessage();
        }
    }
}