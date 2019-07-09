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
import tasker.tasker.entity.User;
import tasker.tasker.mapper.OricaMapperManager;
import tasker.tasker.service.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@ControllerAdvice
@RequiredArgsConstructor
public class UserController {

    private static final String ID = "/{userId}";

    private final UserService userService;
    private final OricaMapperManager mapperManager;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<UserListDto> getUsersList(/*Pageable paged,*/
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return new PageImpl<>(
                this.userService.findAllUsers(page, perPage)
                        .stream()
                        .map(user -> this.mapperManager.map(user, UserListDto.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPageDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(
                this.mapperManager.map(this.userService.findUserById(userId), UserPageDto.class)
        );
    }

    @GetMapping(value = ID + "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<TaskListDto> getTasksForUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return new PageImpl<>(
                this.userService.getTasksForUser(userId, page, perPage)
                        .stream()
                        .map(task -> this.mapperManager.map(task, TaskListDto.class))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDto dto) {
        this.userService.saveUser(this.mapperManager.map(dto, User.class));
    }

    @PatchMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto dto) {
        this.userService.saveUser(this.mapperManager.map(dto, this.userService.findUserById(userId)));
    }

    @DeleteMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}