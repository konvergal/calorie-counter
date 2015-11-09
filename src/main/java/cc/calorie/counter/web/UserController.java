package cc.calorie.counter.web;

import cc.calorie.counter.service.CalorieUserDetails;
import cc.calorie.counter.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * Created by konvergal on 28/10/15.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(
            @RequestBody UsernameAndPasswordDTO usernameAndPasswordDTO,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        userService.createUser(usernameAndPasswordDTO.getUsername(), usernameAndPasswordDTO.getPassword());
        authenticateAndAddToken(usernameAndPasswordDTO, response);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(
            @RequestBody UsernameAndPasswordDTO usernameAndPasswordDTO,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        authenticateAndAddToken(usernameAndPasswordDTO, response);
    }

    private void authenticateAndAddToken(UsernameAndPasswordDTO usernameAndPasswordDTO, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usernameAndPasswordDTO.getUsername(), usernameAndPasswordDTO.getPassword());

        Authentication authenticationResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        CalorieUserDetails user = CalorieUserDetails.class.cast(UsernamePasswordAuthenticationToken.class.cast(authenticationResult).getPrincipal());

        String secret =
                Jwts.builder()
                        .setId(UUID.randomUUID().toString())
                        .setSubject(user.getId().toString())
                        .setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, "secret")
                        .compact();

        Cookie cookie = new Cookie("token", secret);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/api");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

}
