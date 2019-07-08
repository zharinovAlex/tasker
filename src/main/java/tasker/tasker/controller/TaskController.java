package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.task.TaskCreateDto;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.task.TaskPageDto;
import tasker.tasker.dto.task.TaskUpdateDto;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.service.TaskService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/task", produces = "application/json")
@RequiredArgsConstructor
public class TaskController {

    private static final String TASK_ID = "taskId";

    private final TaskService taskService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<TaskListDto> getAllTasks(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return new PageImpl<>(
                this.taskService.getAllTasks(page, perPage)
                        .stream()
                        .map(this::convertToListDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/{" + TASK_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPageDto> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(this.convertToPageDto(taskId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskCreateDto dto) {
        Task task = new Task();

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCreatedBy(dto.getCreatedBy());
        task.setUser(dto.getUser());

        this.taskService.saveTask(task);
    }

    @PatchMapping(value = "/{" + TASK_ID + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskUpdateDto dto) {
        Task task = this.taskService.findTaskById(taskId);

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(dto.getUser());

        this.taskService.saveTask(task);
    }

    @DeleteMapping(value = "/{" + TASK_ID + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskId) {
        this.taskService.deleteTask(taskId);
    }

    private TaskListDto convertToListDto(Task task) {
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

    private TaskPageDto convertToPageDto(Long id) {
        Task task = this.taskService.findTaskById(id);
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
}