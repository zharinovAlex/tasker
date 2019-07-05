package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasker.tasker.entity.Task;
import tasker.tasker.exception.EntityNotFoundException;
import tasker.tasker.repository.TaskRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task findTaskById(Long id) {
        return this.taskRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task with ID %s not found", id)));
    }

    public List<Task> getAllTasks(int page, int perPage) {
        Pageable paged = PageRequest.of(page, perPage);

        Page<Task> tasks = this.taskRepository.findAll(paged);

        return tasks.getContent();
    }

    public void deleteTask(Long id) {
        Task task = this.findTaskById(id);

        this.taskRepository.delete(task);
    }

    public void saveTask(Task task) {
        this.taskRepository.save(task);
    }
}