package cc.calorie.counter.service;

import cc.calorie.counter.exception.DuplicateUserException;
import cc.calorie.counter.model.User;
import cc.calorie.counter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by konvergal on 29/10/15.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        if (user.isPresent()) {
            return new CalorieUserDetails(username, user.get().getPassword(), user.get().getId());
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }

    public User createUser(String username, String password) {
        Optional<User> storedUser = userRepository.findByName(username);
        if (storedUser.isPresent()) {
            throw new DuplicateUserException();
        }

        User user = new User();
        user.setName(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        return userRepository.save(user);
    }

}
