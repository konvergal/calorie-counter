package cc.calorie.counter.web;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by konvergal on 09/11/15.
 */
public class FilterDTO {

    private Optional<Long> userId = Optional.empty();
    private Optional<LocalDateTime> from = Optional.empty();
    private Optional<LocalDateTime> to = Optional.empty();

    public FilterDTO() {
    }

    public Optional<Long> getUserId() {
        return userId;
    }

    public FilterDTO setUserId(Optional<Long> userId) {
        this.userId = userId;
        return this;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Optional<LocalDateTime> getFrom() {
        return from;
    }

    public FilterDTO setFrom(Optional<LocalDateTime> from) {
        this.from = from;
        return this;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Optional<LocalDateTime> getTo() {
        return to;
    }

    public FilterDTO setTo(Optional<LocalDateTime> to) {
        this.to = to;
        return this;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "from=" + from +
                ", to=" + to +
                ", userId=" + userId +
                '}';
    }

}
