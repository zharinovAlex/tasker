package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<User> findAllUsers(int page, int perPage) {
        Pageable paged = PageRequest.of(page, perPage);

        Page<User> users = this.userRepository.findAll(paged);

        return users.getContent();
    }

    public User findUserById(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", id)));

    }

    public List<Task> getTasksForUser(Long userId, int page, int perPage) {
        User user = new User(userId);

        Pageable paged = PageRequest.of(page, perPage);

        return this.taskRepository.findByUser(user, paged);
    }

    public void deleteUser(Long userId) {
        User user = this.findUserById(userId);

        this.userRepository.delete(user);
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}