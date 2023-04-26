package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

public class InvalidUserIdUpdateException extends RuntimeException {

    public InvalidUserIdUpdateException() {
        super(ResponseMessage.INVALID_USER_ID_UPDATE);
    }
}
