package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

public class InvalidWorkoutStateException extends RuntimeException {

    public InvalidWorkoutStateException() {
        super(ResponseMessage.INVALID_WORKOUT_STATE);
    }
}
