package tasker.tasker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tasker.tasker.model.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
@Data
@Entity
@NoArgsConstructor // for hibernate
@AllArgsConstructor // for @builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private Instant updatedAt;

    private TaskStatus status = TaskStatus.STATUS_UNASSIGNED;

    @ManyToOne(targetEntity = User.class)
    private User createdBy;

    @ManyToOne(targetEntity = User.class)
    private User user;
}