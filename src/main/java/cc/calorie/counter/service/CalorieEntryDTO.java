package cc.calorie.counter.service;

import java.time.LocalDateTime;

/**
 * Created by konvergal on 31/10/15.
 */
public class CalorieEntryDTO {

    private Long id;
    private String meal;
    private Integer numberOfCalories;
    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "CalorieEntryDTO{" +
                "id=" + id +
                ", meal='" + meal + '\'' +
                ", numberOfCalories=" + numberOfCalories +
                ", dateTime=" + dateTime +
                '}';
    }

}
