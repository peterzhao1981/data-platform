package com.mode.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mode.base.response.Error;

/**
 * A global controller exception handler that takes the advantage of Spring Controller Advice - see
 * AOP for more detailed descriptions.
 *
 * @author chao
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ModeException.class)
    public Error handleModeException(Exception e) {
        LOG.error("==== MODE EXCEPTION ====");
        e.printStackTrace();
        ModeException me = (ModeException) e;
        return new Error(me.getCode(), me.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public Error handleConstraintViolationException(ConstraintViolationException e) {
        LOG.error("==== CONSTRAINT VIOLATION EXCEPTION ====");
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            builder.append(violation.getMessage() + "\n");
        }
        return new Error(HttpStatus.BAD_REQUEST.value(), builder.toString());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleUnknownException(Exception e) {
        LOG.error("==== UNKNOWN EXCEPTION ====");
        e.printStackTrace();
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}