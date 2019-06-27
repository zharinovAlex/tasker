package tasker.tasker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.entity.Task;
import tasker.tasker.repository.TaskRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping(value = "/task", produces = "application/json")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(value = "id") Long taskId) throws EntityNotFoundException {
        Task task = this.taskRepository.
                findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + taskId + " not found"));

        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody Task task) {
        this.taskRepository.save(task);
    }
}