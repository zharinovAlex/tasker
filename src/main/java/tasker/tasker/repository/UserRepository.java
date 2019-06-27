package tasker.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tasker.tasker.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}