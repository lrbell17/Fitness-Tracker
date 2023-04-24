package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String userId;

    public UserNotFoundException(final String userId) {
        super(String.format(ResponseMessage.USER_ID_NOT_FOUND, userId));
        this.userId = userId;
    }
}
