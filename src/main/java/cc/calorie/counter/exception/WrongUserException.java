package cc.calorie.counter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by konvergal on 07/11/15.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class WrongUserException extends RuntimeException {
}
