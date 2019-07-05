package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.task.TaskListDto;
import tasker.tasker.dto.user.UserCreateDto;
import tasker.tasker.dto.user.UserListDto;
import tasker.tasker.dto.user.UserPageDto;
import tasker.tasker.dto.user.UserUpdateDto;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.service.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@ControllerAdvice
@RequiredArgsConstructor
public class UserController {

    private static final String USER_ID = "userId";

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<UserListDto> getUsersList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return new PageImpl<>(
                this.userService.findAllUsers(page, perPage)
                        .stream()
                        .map(this::convertToListDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/{" + USER_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPageDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(this.convertToPageDto(userId));
    }

    @GetMapping(value = "/{" + USER_ID + "}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<TaskListDto> getTasksForUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return new PageImpl<>(
                this.userService.getTasksForUser(userId, page, perPage)
                        .stream()
                        .map(this::convertToTaskListDto)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDto dto) {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail().toLowerCase());

        if (null != dto.getTeam()) {
            user.setTeam(dto.getTeam());
        }

        this.userService.saveUser(user);
    }

    @PatchMapping(value = "/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto dto) {
        User user = this.userService.findUserById(userId);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setTeam(dto.getTeam());
        user.setActive(dto.getActive());

        this.userService.saveUser(user);
    }

    @DeleteMapping(value = "/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }

    private UserListDto convertToListDto(User user) {
        UserListDto dto = new UserListDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

    private TaskListDto convertToTaskListDto(Task task) {
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

    private UserPageDto convertToPageDto(Long id) {
        User user = this.userService.findUserById(id);
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
}