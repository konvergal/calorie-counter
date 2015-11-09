package cc.calorie.counter.web;

/**
 * Created by konvergal on 03/11/15.
 */
public class UsernameAndPasswordDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsernameAndPasswordDTO{" +
                "username='" + username + '\'' +
                '}';
    }
    
}
