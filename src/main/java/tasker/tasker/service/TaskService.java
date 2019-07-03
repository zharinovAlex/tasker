package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasker.tasker.dto.task.TaskCreateDto;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.task.TaskPageDto;
import tasker.tasker.dto.task.TaskUpdateDto;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.exception.EntityNotFoundException;
import tasker.tasker.repository.TaskRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

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

    public TaskListDto convertToListDto(Task task) {
        TaskListDto dto = new TaskListDto();
        UserListDto userDto = new UserListDto();
        User user = task.getUser();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUser(userDto);

        return dto;
    }

    public TaskPageDto convertToPageDto(Long id) {
        Task task = this.findTaskById(id);
        TaskPageDto dto = new TaskPageDto();

        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCreatedBy(
                UserListDto.builder()
                        .id(task.getCreatedBy().getId())
                        .firstName(task.getCreatedBy().getFirstName())
                        .lastName(task.getCreatedBy().getLastName())
                        .build()
        );
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setStatus(task.getStatus());
        dto.setUser(
                UserListDto.builder()
                        .id(task.getUser().getId())
                        .firstName(task.getUser().getFirstName())
                        .lastName(task.getUser().getLastName())
                        .build()
        );

        return dto;
    }

    public void createTask(TaskCreateDto dto) {
        Task task = new Task();

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCreatedBy(dto.getCreatedBy());
        task.setUser(dto.getUser());

        this.taskRepository.save(task);
    }

    public void updateTask(Long id, TaskUpdateDto dto) {
        Task task = this.findTaskById(id);

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(dto.getUser());

        this.taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = this.findTaskById(id);

        this.taskRepository.delete(task);
    }
}