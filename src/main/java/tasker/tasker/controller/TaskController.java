package tasker.tasker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.TaskListDTO;
import tasker.tasker.dto.TaskPageDTO;
import tasker.tasker.entity.Task;
import tasker.tasker.service.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping(value = "/task", produces = "application/json")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<TaskListDTO> getAllTasks(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        List<Task> tasks = this.taskService.getAllTasks(page, perPage);

        return tasks
                .stream()
                .map(this.taskService::convertToListDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPageDTO> getTask(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.taskService.convertToPageDto(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody Task task) {
        this.taskService.createTask(task);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        this.taskService.updateTask(id, task);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
    }
}