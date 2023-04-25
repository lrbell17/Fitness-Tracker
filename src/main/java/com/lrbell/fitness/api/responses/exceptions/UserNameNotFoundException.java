package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;
import lombok.Getter;

@Getter
public class UserNameNotFoundException extends RuntimeException {

    private String userName;

    public UserNameNotFoundException(final String userName) {
        super(ResponseMessage.USER_NAME_NOT_FOUND);
        this.userName = userName;
    }
}
