package com.epam.malykhin.database.exception;


public class BusinessException extends RuntimeException {
    private Object error;

    public BusinessException(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Something was wrong: \n " + error;
    }
}
