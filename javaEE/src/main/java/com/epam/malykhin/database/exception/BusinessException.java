package com.epam.malykhin.database.exception;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
public class BusinessException extends RuntimeException {
    private Object error;

    public BusinessException(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Connection was something wrong: \n " + error;
    }
}
