package tasker.tasker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tasker.tasker.dictionary.Role;
import tasker.tasker.dictionary.Team;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(name = "first_name", length = 100)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 150)
    @NotNull
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant updatedAt;

    private boolean active = true;

    @JsonIgnore
    private Role role = Role.ROLE_USER;

    private Team team;
}