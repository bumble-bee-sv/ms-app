package dev.sunny.mspayment.exceptions.payment;

public class PaymentApiException extends RuntimeException {

    public PaymentApiException(String message) {
        super(message);
    }

}
