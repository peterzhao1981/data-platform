package com.mode.base;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * State codes for reading/writing to the status columns of database tables.
 *
 * @author chao
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    /*
     * User account statuses.
     *
     * OK - Account status is normal.
     * Anonymous - This is an anonymous account.
     * Unvalidated - Account's email or mobile is not validated.
     * Suspended - Abnormal user activities detected due to various reasons(like login too fast),
     *              and account has been suspended for a while.
     * Locked - User has not logging in for a while, and account has been locked. Or for security
      *             reasons -- security attack from this account; password expired.
     * Closed - User has chosen to delete his/her account.
     */
    ACCOUNT_OK(0, "OK"),
    ACCOUNT_ANONYMOUS(1, "Anonymous"),
    ACCOUNT_UNVALIDATED(2, "Unvalidated"),
    ACCOUNT_SUSPENDED(3, "Suspended"),
    ACCOUNT_LOCKED(4, "Locked"),
    ACCOUNT_CLOSED(5, "Closed"),

    /*
     * Order statuses. See
     * https://support.bigcommerce.com/articles/Public/Understanding-Order-Statuses.
     *
     * Pending — customer started the checkout process, but did not complete it.
     *          Incomplete orders are assigned a "Pending" status.
     * Awaiting Payment — customer has completed checkout process,
     *          but payment has yet to be confirmed.
     * Awaiting Fulfillment — customer has completed the checkout process and
     *          payment has been confirmed.
     * Awaiting Shipment — order has been pulled and packaged,
     *          and is awaiting collection from a shipping provider.
     * Awaiting Pickup — order has been pulled,
     *          and is awaiting customer pickup from a seller-specified location.
     * Partially Shipped — only some items in the order have been shipped, due to some products
     *          being pre-order only or other reasons.
     * Completed — order has been shipped/picked up, and receipt is confirmed; or client has paid
     *          for their digital product and their file(s) are available for download.
     * Shipped — order has been shipped, but receipt has not been confirmed.
     * Cancelled — buyer or seller has cancelled an order, due to various reasons.
     * Refunded — seller has used the Refund action.
     * Disputed — customer has initiated a dispute resolution process for the PayPal transaction
     *          that paid for the order.
     * Verification Required — order on hold while some aspect (e.g. tax-exempt documentation)
     *          needs to be manually confirmed.
     */
    ORDER_PENDING(0, "Pending"),
    ORDER_AWAITING_PAYMENT(1, "Awaiting Payment"),
    ORDER_AWAITING_FULFILLMENT(2, "Awaiting Fulfillment"),
    ORDER_AWAITING_SHIPMENT(3, "Awaiting Shipment"),
    ORDER_AWAITING_PICKUP(4, "Awaiting Pickup"),
    ORDER_PARTIALLY_SHIPPED(5, "Partially Shipped"),
    ORDER_COMPLETED(6, "Completed"),
    ORDER_SHIPPED(7, "Shipped"),
    ORDER_CANCELLED(8, "Cancelled"),
    ORDER_REFUNDED(9, "Refunded"),
    ORDER_DISPUTED(10, "Disputed"),
    ORDER_VERIFICATION_REQUIRED(11, "Verification Required");



    private final int code;
    private final String message;

    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\", \"message\":\"" + message + "\"}";
    }
}