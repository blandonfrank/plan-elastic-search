package com.personal.plan.elasticsearch.exception;

/**
 * Exception that can be thrown when searching for a plan
 */
public class PlanSearchException extends RuntimeException{

    public PlanSearchException() {
    }

    public PlanSearchException(String message) {
        super(message);
    }

    public PlanSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanSearchException(Throwable cause) {
        super(cause);
    }

    public PlanSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
