package tasker.tasker.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tasker.tasker.dto.UserListDTO;
import tasker.tasker.dto.UserPageDTO;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;
import tasker.tasker.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user", produces = "application/json")
@ControllerAdvice
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<UserListDTO> getUsersList(
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
    public ResponseEntity<UserPageDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.userService.convertToPageDto(id));
    }

    @GetMapping(value = "/{userId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<Task> getTasksForUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage
    ) {
        return this.userService.getTasksForUser(userId, page, perPage);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody User user) {
        this.userService.createUser(user);
    }

    @PatchMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody User user) {
        this.userService.updateUser(userId, user);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}