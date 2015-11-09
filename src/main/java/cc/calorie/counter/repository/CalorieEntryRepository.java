package cc.calorie.counter.repository;

import cc.calorie.counter.model.CalorieEntry;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by konvergal on 31/10/15.
 */
@Repository
public interface CalorieEntryRepository extends CrudRepository<CalorieEntry, Long>, JpaSpecificationExecutor<CalorieEntry> {

}
