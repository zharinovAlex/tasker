package tasker.tasker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tasker.tasker.dto.UserListDTO;
import tasker.tasker.dto.UserPageDTO;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.exception.EntityNotFoundException;
import tasker.tasker.repository.TaskRepository;
import tasker.tasker.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    public UserService(
            UserRepository userRepository,
            TaskRepository taskRepository
    ) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<User> findAllUsers(int page, int perPage) {
        Pageable paged = PageRequest.of(page, perPage);

        Page<User> users = this.userRepository.findAll(paged);

        return users.getContent();
    }

    private User findUserById(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", id)));

    }

    public void createUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());

        this.userRepository.save(user);
    }

    public Page<Task> getTasksForUser(Long userId, int page, int perPage) {
        User user = new User();
        user.setId(userId);

        Pageable paged = PageRequest.of(page, perPage);

        return this.taskRepository.findByUser(user, paged);
    }

    public void updateUser(Long userId, User updatedUser) {
        User user = this.findUserById(userId);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setTeam(updatedUser.getTeam());
        user.setActive(updatedUser.isActive());

        this.userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = this.findUserById(userId);

        this.userRepository.delete(user);
    }

    public UserListDTO convertToListDto(User user) {
        UserListDTO dto = new UserListDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

    public UserPageDTO convertToPageDto(Long id) {
        User user = this.findUserById(id);
        UserPageDTO dto = new UserPageDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setActive(user.isActive());
        dto.setTeam(user.getTeam());

        return dto;
    }
}