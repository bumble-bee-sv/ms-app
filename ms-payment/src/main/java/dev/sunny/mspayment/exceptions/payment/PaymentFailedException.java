package dev.sunny.mspayment.exceptions.payment;

public class PaymentFailedException extends PaymentApiException {

    public PaymentFailedException(String message) {
        super(message);
    }

}
