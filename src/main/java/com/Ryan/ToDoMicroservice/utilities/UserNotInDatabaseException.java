package com.Ryan.ToDoMicroservice.utilities;

public class UserNotInDatabaseException extends Exception {
    public UserNotInDatabaseException(String msg) {
        super(msg);
    }
}
