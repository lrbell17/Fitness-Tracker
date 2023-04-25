package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;
import lombok.Getter;

@Getter
public class InvalidWorkoutStateException extends RuntimeException {

    private String workoutId;
    public InvalidWorkoutStateException(final String workoutId) {
        super(ResponseMessage.INVALID_WORKOUT_STATE);
        this.workoutId = workoutId;
    }
}
