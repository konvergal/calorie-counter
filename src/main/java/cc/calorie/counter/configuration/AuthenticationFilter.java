package cc.calorie.counter.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by konvergal on 04/11/15.
 */
public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(request);
        Cookie[] cookies = httpServletRequest.getCookies();
        Optional<Cookie> tokenCookie = getTokenCookie(cookies);

        if (tokenCookie.isPresent()) {
            Object secret = Jwts.parser().setSigningKey("secret").parse(tokenCookie.get().getValue()).getBody();
            DefaultClaims defaultClaims = DefaultClaims.class.cast(secret);
            String userIdAsString = defaultClaims.getSubject();
            Authentication authentication = createAuthentication(Long.valueOf(userIdAsString));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
            SecurityContextHolder.clearContext();
        } else {
            chain.doFilter(request, response);
        }
    }

    private Authentication createAuthentication(Long userId) {
        AbstractAuthenticationToken abstractAuthenticationToken = new AbstractAuthenticationToken(Collections.emptySet()) {
            @Override
            public Object getCredentials() {
                return "";
            }

            @Override
            public Object getPrincipal() {
                return new CalorieAuthenticationPrincipal(userId);
            }
        };
        abstractAuthenticationToken.setAuthenticated(true);
        return abstractAuthenticationToken;
    }

    private Optional<Cookie> getTokenCookie(Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }

}
