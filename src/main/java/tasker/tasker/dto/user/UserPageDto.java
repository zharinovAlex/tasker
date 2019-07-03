package tasker.tasker.dto.user;

import lombok.Data;
import tasker.tasker.model.Team;

import java.time.Instant;

@Data
public class UserPageDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant updatedAt;
    private Boolean active;
    private Team team;
}