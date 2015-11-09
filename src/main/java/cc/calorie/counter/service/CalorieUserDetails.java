package cc.calorie.counter.service;

import java.util.ArrayList;

/**
 * Created by konvergal on 07/11/15.
 */
public class CalorieUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public CalorieUserDetails(String username, String password, Long id) {
        super(username, password, new ArrayList<>());
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
