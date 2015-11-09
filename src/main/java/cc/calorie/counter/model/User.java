package cc.calorie.counter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by konvergal on 28/10/15.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer expectedNumOfCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getExpectedNumOfCalories() {
        return expectedNumOfCalories;
    }

    public void setExpectedNumOfCalories(Integer expectedNumOfCalories) {
        this.expectedNumOfCalories = expectedNumOfCalories;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", expectedNumOfCalories=" + expectedNumOfCalories +
                ", id=" + id +
                '}';
    }

}
