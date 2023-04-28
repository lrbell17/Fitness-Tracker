package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String userId) {
        super(String.format(ResponseMessage.USER_ID_NOT_FOUND, userId));
    }
}
