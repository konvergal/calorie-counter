package cc.calorie.counter;

import cc.calorie.counter.exception.WrongUserException;
import cc.calorie.counter.model.User;
import cc.calorie.counter.service.CalorieEntryDTO;
import cc.calorie.counter.service.CalorieEntryService;
import cc.calorie.counter.service.UserService;
import cc.calorie.counter.web.FilterDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

/**
 * Created by konvergal on 07/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalorieCounterApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CalorieEntryServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CalorieEntryService calorieEntryService;

    private User createUser() {
        return userService.createUser("username", "password");
    }

    @Test
    public void testAddCalorieEntry() {
        User user = createUser();

        CalorieEntryDTO calorieEntryDTO = new CalorieEntryDTO();
        calorieEntryDTO.setMeal("text");
        calorieEntryDTO.setNumberOfCalories(123);
        calorieEntryDTO.setDateTime(LocalDateTime.now());
        CalorieEntryDTO calorieEntryDTOs = calorieEntryService.addCalorieEntry(calorieEntryDTO, user.getId());
        Assert.assertNotNull(calorieEntryDTOs.getId());
    }

    @Test
    public void testFindCalorieEntries() {
        User user1 = userService.createUser("username1", "password");
        User user2 = userService.createUser("username2", "password");

        CalorieEntryDTO calorieEntryDTO1 = createCalorieEntryDTO("text1", 123, LocalDateTime.now(), user1);
        CalorieEntryDTO calorieEntryDTO2 = createCalorieEntryDTO("text2", 456, LocalDateTime.of(2015, Month.MARCH, 30, 12, 21, 59), user2);
        CalorieEntryDTO calorieEntryDTO3 = createCalorieEntryDTO("text3", 789, LocalDateTime.of(2016, Month.FEBRUARY, 15, 10, 10, 10), user2);

        List<CalorieEntryDTO> calorieEntries1 = calorieEntryService.findCalorieEntries(new FilterDTO().setUserId(Optional.of(user1.getId())));
        Assert.assertEquals(1, calorieEntries1.size());
        Assert.assertEquals(calorieEntryDTO1.getId(), calorieEntries1.get(0).getId());

        List<CalorieEntryDTO> calorieEntries2 = calorieEntryService.findCalorieEntries(new FilterDTO().setUserId(Optional.of(user2.getId())));
        Assert.assertEquals(2, calorieEntries2.size());
        Assert.assertEquals(calorieEntryDTO3.getId(), calorieEntries2.get(0).getId());
        Assert.assertEquals(calorieEntryDTO2.getId(), calorieEntries2.get(1).getId());

        List<CalorieEntryDTO> calorieEntries3 = calorieEntryService.findCalorieEntries(
                new FilterDTO()
                        .setFrom(Optional.of(LocalDateTime.of(2015, Month.JANUARY, 1, 1, 1, 1)))
                        .setTo(Optional.of(LocalDateTime.of(2016, Month.JANUARY, 1, 1, 1, 1)))
                        .setUserId(Optional.of(user2.getId())));
        Assert.assertEquals(1, calorieEntries3.size());
        Assert.assertEquals(calorieEntryDTO2.getId(), calorieEntries3.get(0).getId());

        List<CalorieEntryDTO> calorieEntries4 = calorieEntryService.findCalorieEntries(
                new FilterDTO()
                        .setTo(Optional.of(LocalDateTime.of(2016, Month.JANUARY, 1, 1, 1, 1)))
                        .setUserId(Optional.of(user2.getId())));
        Assert.assertEquals(1, calorieEntries4.size());
        Assert.assertEquals(calorieEntryDTO2.getId(), calorieEntries3.get(0).getId());
    }

    private CalorieEntryDTO createCalorieEntryDTO(String text, Integer numberOfCalories, LocalDateTime dateTime, User user) {
        CalorieEntryDTO calorieEntryDTO = new CalorieEntryDTO();
        calorieEntryDTO.setMeal(text);
        calorieEntryDTO.setNumberOfCalories(numberOfCalories);
        calorieEntryDTO.setDateTime(dateTime);
        return calorieEntryService.addCalorieEntry(calorieEntryDTO, user.getId());
    }

    @Test
    public void testDeleteCalorieEntry() {
        User user = userService.createUser("username1", "password");
        CalorieEntryDTO calorieEntryDTO1 = createCalorieEntryDTO("text1", 123, LocalDateTime.now(), user);
        calorieEntryService.deleteCalorieEntry(calorieEntryDTO1.getId(), user.getId());

        List<CalorieEntryDTO> calorieEntries = calorieEntryService.findCalorieEntries(
                new FilterDTO().setUserId(Optional.of(user.getId())));

        Assert.assertEquals(0, calorieEntries.size());
    }

    @Test(expected = WrongUserException.class)
    public void testDeleteCalorieEntryWithBadUser() {
        User user = userService.createUser("username1", "password");
        CalorieEntryDTO calorieEntryDTO1 = createCalorieEntryDTO("text1", 123, LocalDateTime.now(), user);
        calorieEntryService.deleteCalorieEntry(calorieEntryDTO1.getId(), user.getId() + 1);
    }

    @Test
    public void testUpdateCalorieEntry() {

    }

}
