package tasker.tasker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tasker.tasker.model.Role;
import tasker.tasker.model.Team;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private Boolean active = true;

    @JsonIgnore
    private Role role = Role.ROLE_USER;

    private Team team;

    public User(@NotNull Long id) {
        this.id = id;
    }
}