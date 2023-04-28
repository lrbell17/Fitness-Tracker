package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

public class UserNameNotFoundException extends RuntimeException {

    public UserNameNotFoundException(final String userName) {
        super(String.format(ResponseMessage.USER_NAME_NOT_FOUND, userName));
    }
}
