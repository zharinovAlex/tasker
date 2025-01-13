package tasker.tasker.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApplicationServiceTest {

    @InjectMocks
    ApplicationService applicationService;

    @Mock
    UserService userService;

    @Mock
    TaskService taskService;

    @Mock
    List<User> userList;

    @Mock
    List<Task> taskList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers() {
        when(userService.findAllUsers(any(Pageable.class))).thenReturn(userList);

        List<User> allUsers = applicationService.getUsers(Pageable.unpaged());

        assertEquals(userList, allUsers);
    }

    @Test
    public void getUser() {
        User user = new User();

        when(userService.findUserById(any(Long.class))).thenReturn(user);

        User foundUser = applicationService.getUser(new Long(123));

        assertEquals(user, foundUser);
    }

    @Test
    public void getUsersTasks() {
        when(userService.getTasksForUser(any(Long.class), any(Pageable.class))).thenReturn(taskList);

        List<Task> userTasks = applicationService.getUsersTasks(new Long(123), Pageable.unpaged());

        assertEquals(taskList, userTasks);
    }

    @Test
    public void getTasks() {
        when(taskService.getAllTasks(any(Pageable.class))).thenReturn(taskList);

        List<Task> tasks = applicationService.getTasks(Pageable.unpaged());

        assertEquals(taskList, tasks);
    }

    @Test
    public void getTask() {
        Task task = new Task();

        when(taskService.findTaskById(any(Long.class))).thenReturn(task);

        Task foundTask = applicationService.getTask(new Long(123));

        assertEquals(task, foundTask);
    }
}