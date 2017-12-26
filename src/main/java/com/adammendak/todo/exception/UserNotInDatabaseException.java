package com.adammendak.todo.exceptions;

public class UserNotInDatabaseException extends Exception{

    public UserNotInDatabaseException(String message) {
        super(message);
    }
}
