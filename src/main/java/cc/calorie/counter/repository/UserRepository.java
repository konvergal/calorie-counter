package cc.calorie.counter.repository;

import cc.calorie.counter.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by konvergal on 29/10/15.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String username);

}
