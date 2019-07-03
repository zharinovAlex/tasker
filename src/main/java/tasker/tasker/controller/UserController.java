package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@ControllerAdvice
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<UserListDto> getUsersList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        List<User> users = this.userService.findAllUsers(page, perPage);

        return users
                .stream()
                .map(this.userService::convertToListDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPageDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.userService.convertToPageDto(id));
    }

    @GetMapping(value = "/{userId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<TaskListDto> getTasksForUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        List<Task> tasks = this.userService.getTasksForUser(userId, page, perPage);

        return tasks
                .stream()
                .map(this.userService::convertToTaskListDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDto dto) {
        this.userService.createUser(dto);
    }

    @PatchMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto dto) {
        this.userService.updateUser(userId, dto);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}