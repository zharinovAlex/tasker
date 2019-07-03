package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.task.TaskCreateDto;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.task.TaskPageDto;
import tasker.tasker.dto.task.TaskUpdateDto;
import tasker.tasker.entity.Task;
import tasker.tasker.service.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/task", produces = "application/json")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<TaskListDto> getAllTasks(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        List<Task> tasks = this.taskService.getAllTasks(page, perPage);

        return tasks
                .stream()
                .map(this.taskService::convertToListDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPageDto> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(this.taskService.convertToPageDto(taskId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskCreateDto dto) {
        this.taskService.createTask(dto);
    }

    @PatchMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskUpdateDto dto) {
        this.taskService.updateTask(taskId, dto);
    }

    @DeleteMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskId) {
        this.taskService.deleteTask(taskId);
    }
}