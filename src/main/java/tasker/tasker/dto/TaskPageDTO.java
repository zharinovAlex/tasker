package tasker.tasker.dto;

import lombok.Data;
import tasker.tasker.dictionary.TaskStatus;
import tasker.tasker.entity.User;

import java.time.Instant;

@Data
public class TaskPageDTO {

    private long id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private User createdBy;

    private User user;

    public TaskPageDTO setId(long id) {
        this.id = id;

        return this;
    }
}