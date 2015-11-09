package cc.calorie.counter.service;

import cc.calorie.counter.exception.WrongUserException;
import cc.calorie.counter.model.CalorieEntry;
import cc.calorie.counter.model.User;
import cc.calorie.counter.repository.CalorieEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by konvergal on 31/10/15.
 */
@Service
public class CalorieEntryService {

    @Autowired
    private CalorieEntryRepository calorieEntryRepository;

    public List<CalorieEntryDTO> addCalorieEntry(CalorieEntryDTO calorieEntryDTO, Long userId) {
        Objects.requireNonNull(calorieEntryDTO);
        Objects.requireNonNull(userId);

        CalorieEntry calorieEntry = new CalorieEntry();
        calorieEntry.setDateTime(calorieEntryDTO.getDateTime());
        calorieEntry.setMeal(calorieEntryDTO.getMeal());
        calorieEntry.setNumberOfCalories(calorieEntryDTO.getNumberOfCalories());
        User user = new User();
        user.setId(userId);
        calorieEntry.setUser(user);
        calorieEntryRepository.save(calorieEntry);

        return findCalorieEntries(Optional.empty(), Optional.empty(), userId);
    }

    public List<CalorieEntryDTO> findCalorieEntries(Optional<LocalDateTime> fromDate, Optional<LocalDateTime> toDate, Long userId) {
        Objects.requireNonNull(fromDate);
        Objects.requireNonNull(toDate);
        Objects.requireNonNull(userId);

        List<CalorieEntry> calorieEntries = calorieEntryRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("user").get("id"), userId);

            if (fromDate.isPresent()) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<LocalDateTime>get("dateTime"), fromDate.get()));
            }

            if (toDate.isPresent()) {
                predicate = cb.and(cb.lessThanOrEqualTo(root.<LocalDateTime>get("dateTime"), toDate.get()));
            }

            return predicate;
        }, new Sort(Sort.Direction.DESC, "dateTime"));

        List<CalorieEntryDTO> result = calorieEntries.stream().map(CalorieEntryToDTO.INSTANCE).collect(Collectors.toList());
        return result;
    }

    public void deleteCalorieEntry(Long calorieEntryId, Long userId) {
        Objects.requireNonNull(calorieEntryId);
        Objects.requireNonNull(userId);

        CalorieEntry calorieEntry = calorieEntryRepository.findOne(calorieEntryId);
        if (calorieEntry != null) {
            if (!userId.equals(calorieEntry.getUser().getId())) {
                throw new WrongUserException();
            }
            calorieEntryRepository.delete(calorieEntryId);
        }
    }

    public void updateCalorieEntry(CalorieEntryDTO calorieEntryDTO, Long userId) {
        Objects.requireNonNull(calorieEntryDTO);
        Objects.requireNonNull(calorieEntryDTO.getId());
        Objects.requireNonNull(userId);

        CalorieEntry calorieEntry = calorieEntryRepository.findOne(calorieEntryDTO.getId());

        if (!userId.equals(calorieEntry.getUser().getId())) {
            throw new WrongUserException();
        }

        calorieEntry.setMeal(calorieEntryDTO.getMeal());
        calorieEntry.setNumberOfCalories(calorieEntryDTO.getNumberOfCalories());
        calorieEntry.setDateTime(calorieEntryDTO.getDateTime());
    }

    public enum CalorieEntryToDTO implements Function<CalorieEntry, CalorieEntryDTO> {

        INSTANCE;

        @Override
        public CalorieEntryDTO apply(CalorieEntry calorieEntry) {
            CalorieEntryDTO result = new CalorieEntryDTO();
            result.setId(calorieEntry.getId());
            result.setNumberOfCalories(calorieEntry.getNumberOfCalories());
            result.setMeal(calorieEntry.getMeal());
            result.setDateTime(calorieEntry.getDateTime());
            return result;
        }

    }



}
