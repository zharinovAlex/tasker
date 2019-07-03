package tasker.tasker.dto.task;

import lombok.Data;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.model.TaskStatus;

import java.time.Instant;

@Data
public class TaskPageDto {

    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private UserListDto createdBy;
    private UserListDto user;
}