package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.exception.EntityNotFoundException;
import tasker.tasker.repository.TaskRepository;
import tasker.tasker.repository.UserRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public List<User> findAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable).getContent();
    }

    public User findUserById(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", id)));

    }

    public List<Task> getTasksForUser(Long userId, Pageable pageable) {
        return this.taskRepository.findByUser(this.findUserById(userId), pageable);
    }

    public void deleteUser(Long userId) {
        this.userRepository.delete(this.findUserById(userId));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}