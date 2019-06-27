package tasker.tasker.dto;

import lombok.Data;
import tasker.tasker.dictionary.Team;

import java.time.Instant;

@Data
public class UserPageDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant updatedAt;
    private boolean active;
    private Team team;

}