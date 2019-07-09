package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.task.TaskCreateDto;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.task.TaskPageDto;
import tasker.tasker.dto.task.TaskUpdateDto;
import tasker.tasker.entity.Task;
import tasker.tasker.mapper.OricaMapperManager;
import tasker.tasker.service.TaskService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/task", produces = "application/json")
@RequiredArgsConstructor
public class TaskController {

    private static final String TASK_ID = "/{taskId}";

    private final TaskService taskService;
    private final OricaMapperManager mapperManager;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<TaskListDto> getAllTasks(Pageable pageable) {
        return new PageImpl<>(
                this.taskService.getAllTasks(pageable)
                        .stream()
                        .map(task -> this.mapperManager.map(task, TaskListDto.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = TASK_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPageDto> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(this.mapperManager.map(this.taskService.findTaskById(taskId), TaskPageDto.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskCreateDto dto) {
        this.taskService.saveTask(this.mapperManager.map(dto, Task.class));
    }

    @PatchMapping(value = TASK_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskUpdateDto dto) {
        this.taskService.saveTask(this.mapperManager.map(dto, this.taskService.findTaskById(taskId)));
    }

    @DeleteMapping(value = TASK_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskId) {
        this.taskService.deleteTask(taskId);
    }
}