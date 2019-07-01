package tasker.tasker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tasker.tasker.dto.TaskListDTO;
import tasker.tasker.dto.TaskPageDTO;
import tasker.tasker.entity.Task;
import tasker.tasker.exception.EntityNotFoundException;
import tasker.tasker.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task findTaskById(Long id) {
        return this.taskRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task with ID %s not found", id)));
    }

    public List<Task> getAllTasks(int page, int perPage) {
        Pageable paged = PageRequest.of(page, perPage);

        Page<Task> tasks = this.taskRepository.findAll(paged);

        return tasks.getContent();
    }

    public TaskListDTO convertToListDto(Task task) {
        TaskListDTO dto = new TaskListDTO();

        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUser(task.getUser());

        return dto;
    }

    public TaskPageDTO convertToPageDto(Long id) {
        Task task = this.findTaskById(id);
        TaskPageDTO dto = new TaskPageDTO();

        dto
                .setId(task.getId())
                .setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCreatedBy(task.getCreatedBy());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setStatus(task.getStatus());
        dto.setUser(task.getUser());

        return dto;
    }

    public void createTask(Task task) {
        this.taskRepository.save(task);
    }

    public void updateTask(Long id, Task updatedTask) {
        Task task = this.findTaskById(id);

        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setUser(updatedTask.getUser());

        this.taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = this.findTaskById(id);

        this.taskRepository.delete(task);
    }
}