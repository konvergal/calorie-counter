package cc.calorie.counter;

import cc.calorie.counter.exception.DuplicateUserException;
import cc.calorie.counter.model.User;
import cc.calorie.counter.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by konvergal on 07/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalorieCounterApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private final String username = "username";
    private final String password = "password";

    @Test
    public void testCreateUser() {
        User user = userService.createUser(username, password);
        Assert.assertNotNull(user);
    }

    @Test
    public void testLoadCreatedUser() {
        userService.createUser(username, password);
        UserDetails userDetails = userService.loadUserByUsername(username);
        Assert.assertNotNull(userDetails);
    }

    @Test(expected = DuplicateUserException.class)
    public void testCreateDuplicatedUser() {
        userService.createUser(username, password);
        userService.createUser(username, password);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserNotFound() {
        userService.loadUserByUsername("apple");
    }

}
