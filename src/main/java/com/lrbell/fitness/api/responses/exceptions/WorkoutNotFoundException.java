package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;
import lombok.Getter;

@Getter
public class WorkoutNotFoundException extends RuntimeException {

    private final String workoutId;

    public WorkoutNotFoundException(final String workoutId) {
        super(ResponseMessage.USER_ID_NOT_FOUND);
        this.workoutId = workoutId;
    }
}
