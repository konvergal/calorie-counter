package cc.calorie.counter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by konvergal on 28/10/15.
 */
@Entity
public class CalorieEntry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    private LocalDateTime dateTime;

    private String meal;

    private Integer numberOfCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Integer getNumberOfCalories() {
        return numberOfCalories;
    }

    public void setNumberOfCalories(Integer numberOfCalories) {
        this.numberOfCalories = numberOfCalories;
    }

    @Override
    public String toString() {
        return "CalorieEntry{" +
                "id=" + id +
                ", user=" + user +
                ", dateTime=" + dateTime +
                ", meal='" + meal + '\'' +
                ", numberOfCalories=" + numberOfCalories +
                '}';
    }

}
