package tasker.tasker.dto.task;

import lombok.Getter;
import tasker.tasker.entity.Task;
import tasker.tasker.model.TaskStatus;

@Getter
public class TaskReportDto {

    private Long taskId;
    private TaskStatus previousStatus;
    private TaskStatus currentStatus;
    private String taskName;

    public TaskReportDto(TaskStatus originalStatus, Task updatedTask) {
        this.taskId = updatedTask.getId();
        this.previousStatus = originalStatus;
        this.currentStatus = updatedTask.getStatus();
        this.taskName = updatedTask.getName();
    }
}