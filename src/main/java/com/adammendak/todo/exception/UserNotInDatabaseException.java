package com.adammendak.todo.exception;

public class UserNotInDatabaseException extends Exception{

    public UserNotInDatabaseException(String message) {
        super(message);
    }
}
