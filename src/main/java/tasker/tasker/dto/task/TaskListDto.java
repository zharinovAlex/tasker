package tasker.tasker.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.model.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDto {

    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private UserListDto user;
}