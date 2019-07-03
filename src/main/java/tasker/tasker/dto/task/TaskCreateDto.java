package tasker.tasker.dto.task;

import lombok.Getter;
import tasker.tasker.entity.User;

import javax.validation.constraints.NotBlank;

@Getter
public class TaskCreateDto {

    @NotBlank
    private String name;
    private String description;
    private User createdBy;
    private User user;
}