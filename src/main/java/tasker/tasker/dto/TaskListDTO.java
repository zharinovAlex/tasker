package tasker.tasker.dto;

import lombok.Data;
import tasker.tasker.dictionary.TaskStatus;
import tasker.tasker.entity.User;

@Data
public class TaskListDTO {

    private long id;

    private String name;

    private String description;

    private TaskStatus status;

    private User user;
}