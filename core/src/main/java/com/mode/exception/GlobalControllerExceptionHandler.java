package com.mode.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mode.base.ErrorResponse;

/**
 * A global controller exception handler that takes the advantage of Spring Controller Advice - see
 * AOP for more detailed descriptions.
 *
 * @author chao
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(ModeException.class)
    public ErrorResponse handleModeException(Exception e) {

        LOG.error("==== MODE EXCEPTION ====");
        e.printStackTrace();
        ModeException me = (ModeException) e;
        return new ErrorResponse(me.getCode(), me.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnknownException(Exception e) {

        LOG.error("==== UNKNOWN EXCEPTION ====");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
    }
}