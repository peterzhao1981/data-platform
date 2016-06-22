package com.mode.base;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A global response message definition. To support globalization, please note that the message code
 * is the only identifier for mapping the English description to its corresponding
 * descriptions in other languages. Descriptions in non-English languages are defined in the
 * database md_config table.
 * <p/>
 * A non-zero message code means that an error or an exception happened for this api call.
 * Created by Lei on 3/23/16.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Message {
    /*
     * Message code from 40X to 50X are reserved.
     *
     * A non-200 message code means that an error or an exception happened for this api call.
     */
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    TIMEOUT(408, "Request Timeout"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /*
     * User related errors (account, profile, VIP, social): 1 - 49.
     */
    ACCOUNT_LOCKED(1, "Account Locked");

    /*
     * Content (feeds, products, etc) related errors: 50 - 99.
     */

    /*
     * Order/Payment/Shipping related errors: 100 - 199.
     */

    private int code;
    private String description;

    Message(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\", \"description\":\"" + description + "\"}";
    }
}
