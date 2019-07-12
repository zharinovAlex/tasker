package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.model.TaskStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserService userService;
    private final TaskService taskService;
    private final ReportingService reportingService;

    public List<User> getUsers(Pageable pageable) {
        return this.userService.findAllUsers(pageable);
    }

    public User getUser(Long id) {
        return this.userService.findUserById(id);
    }

    public List<Task> getUsersTasks(Long userId, Pageable pageable) {
        return this.userService.getTasksForUser(userId, pageable);
    }

    public void saveUser(User user) {
        this.userService.saveUser(user);
    }

    public void deleteUser(Long userId) { this.userService.deleteUser(userId); }

    public List<Task> getTasks(Pageable pageable) {
        return this.taskService.getAllTasks(pageable);
    }

    public Task getTask(Long id) {
        return this.taskService.findTaskById(id);
    }

    public void createTask(Task task) {
        this.taskService.saveTask(task);
    }

    public void updateTask(TaskStatus originalStatus, Task updatedTask) {
        this.taskService.saveTask(updatedTask);

        if (originalStatus != updatedTask.getStatus()) {
            this.reportingService.taskStatusChangedReport(originalStatus, updatedTask);
        }
    }

    public void deleteTask(Long id) {
        this.taskService.deleteTask(id);
    }
}