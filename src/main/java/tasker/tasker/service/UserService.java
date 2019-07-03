package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.user.UserCreateDto;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.dto.user.UserPageDto;
import tasker.tasker.dto.user.UserUpdateDto;
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

    private User findUserById(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", id)));

    }

    public void createUser(UserCreateDto dto) {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail().toLowerCase());

        if (null != dto.getTeam()) {
            user.setTeam(dto.getTeam());
        }

        this.userRepository.save(user);
    }

    public List<Task> getTasksForUser(Long userId, int page, int perPage) {
        User user = new User(userId);

        Pageable paged = PageRequest.of(page, perPage);

        return this.taskRepository.findByUser(user, paged);
    }

    public void updateUser(Long userId, UserUpdateDto dto) {
        User user = this.findUserById(userId);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setTeam(dto.getTeam());
        user.setActive(dto.getActive());

        this.userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = this.findUserById(userId);

        this.userRepository.delete(user);
    }

    public UserListDto convertToListDto(User user) {
        UserListDto dto = new UserListDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

    public UserPageDto convertToPageDto(Long id) {
        User user = this.findUserById(id);
        UserPageDto dto = new UserPageDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setActive(user.getActive());
        dto.setTeam(user.getTeam());

        return dto;
    }

    public TaskListDto convertToTaskListDto(Task task) {
        return TaskListDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .user(
                        UserListDto.builder()
                                .id(task.getUser().getId())
                                .firstName(task.getUser().getFirstName())
                                .lastName(task.getUser().getLastName())
                                .build()
                )
                .build();
    }
}