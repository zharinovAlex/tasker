package tasker.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import tasker.tasker.service.ApplicationService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private static final String USER_ID = "/{userId}";

    private final OricaMapperManager mapperManager;
    private final ApplicationService applicationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<UserListDto> getUsersList(Pageable pageable) {
        return new PageImpl<>(
                this.applicationService.getUsers(pageable)
                        .stream()
                        .map(user -> this.mapperManager.map(user, UserListDto.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPageDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(
                this.mapperManager.map(this.applicationService.getUser(userId), UserPageDto.class)
        );
    }

    @GetMapping(value = USER_ID + "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<TaskListDto> getTasksForUser(@PathVariable Long userId, Pageable pageable) {
        return new PageImpl<>(
                this.applicationService.getUsersTasks(userId, pageable)
                        .stream()
                        .map(task -> this.mapperManager.map(task, TaskListDto.class))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDto dto) {
        this.applicationService.saveUser(this.mapperManager.map(dto, User.class));
    }

    @PatchMapping(value = USER_ID)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto dto) {
        this.applicationService.saveUser(this.mapperManager.map(dto, this.applicationService.getUser(userId)));
    }

    @DeleteMapping(value = USER_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.applicationService.deleteUser(userId);
    }
}