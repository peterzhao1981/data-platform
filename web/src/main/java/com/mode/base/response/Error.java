package com.mode.base.response;

import java.util.Date;
import java.util.Map;

/**
 * This is the error object that represents the server responses to bad client requests. The
 * error response object consists of the error code, error service, and timestamp.
 * <p>
 * Please don't change this class, as it will impact the mobile clients' internationalization
 * which will require users to upgrade the client app.
 *
 * @author chao
 */
public final class Error {

    public Integer code;
    public String message;
    public String timestamp;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = new Date().toString();
    }

    public Error(int code, Map<String, Object> errorAttributes) {
        this.code = code;
        this.message = errorAttributes.get("message").toString();
        this.timestamp = errorAttributes.get("timestamp").toString();
    }

}