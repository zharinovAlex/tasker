package tasker.tasker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import tasker.tasker.dictionary.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotNull
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at")
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    private TaskStatus status = TaskStatus.STATUS_UNASSIGNED;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User createdBy;

    @ManyToOne(targetEntity = User.class)
    private User user;
}