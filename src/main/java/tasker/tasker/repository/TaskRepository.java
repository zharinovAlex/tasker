package tasker.tasker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tasker.tasker.entity.Task;
import tasker.tasker.entity.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

//    @Query(nativeQuery = true, value = "SELECT t FROM task t WHERE user_id = :userId")
    Page<Task> findByUser(User user, Pageable pageable);
}