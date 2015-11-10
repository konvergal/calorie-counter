package cc.calorie.counter.service;

import cc.calorie.counter.model.CalorieEntry;
import cc.calorie.counter.web.FilterDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by konvergal on 10/11/15.
 */
public class FindCalorieEntriesSpecification implements Specification<CalorieEntry> {

    private final FilterDTO filterDTO;

    public FindCalorieEntriesSpecification(FilterDTO filterDTO) {
        this.filterDTO = Objects.requireNonNull(filterDTO);
    }

    @Override
    public Predicate toPredicate(Root<CalorieEntry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Optional<Predicate> predicate = Optional.empty();

        if (filterDTO.getUserId().isPresent()) {
            predicate = and(predicate, cb.equal(root.get("user").get("id"), filterDTO.getUserId().get()), cb);
        }

        if (filterDTO.getFrom().isPresent()) {
            predicate = and(predicate, cb.greaterThanOrEqualTo(root.<LocalDateTime>get("dateTime"), filterDTO.getFrom().get()), cb);
        }

        if (filterDTO.getTo().isPresent()) {
            predicate = and(predicate, cb.lessThanOrEqualTo(root.<LocalDateTime>get("dateTime"), filterDTO.getTo().get()), cb);
        }

        return predicate.orElse(null);
    }

    private Optional<Predicate> and(Optional<Predicate> optional, Predicate predicate, CriteriaBuilder cb) {
        Objects.requireNonNull(optional);
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(cb);

        if (optional.isPresent()) {
            return Optional.of(cb.and(optional.get(), predicate));
        } else {
            return Optional.of(predicate);
        }
    }

}
