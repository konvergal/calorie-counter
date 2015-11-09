package cc.calorie.counter.configuration;

import java.util.Objects;

/**
 * Created by konvergal on 07/11/15.
 */
public class CalorieAuthenticationPrincipal {

    private final Long userId;

    public CalorieAuthenticationPrincipal(Long userId) {
        this.userId = Objects.requireNonNull(userId);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CalorieAuthenticationPrincipal{" +
                "userId=" + userId +
                '}';
    }

}
