package tasker.tasker.dto.task;

import lombok.Getter;
import tasker.tasker.entity.User;
import tasker.tasker.model.TaskStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class TaskUpdateDto {

    @NotBlank
    private String name;
    private String description;
    @NotNull
    private TaskStatus status;
    private User user;
}