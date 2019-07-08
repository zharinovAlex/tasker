package tasker.tasker.dto.user;

import lombok.Data;
import tasker.tasker.model.Team;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdateDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(message = "Email should be valid")
    @NotNull
    private String email;

    @NotNull
    private Boolean active = true;

    private Team team;
}