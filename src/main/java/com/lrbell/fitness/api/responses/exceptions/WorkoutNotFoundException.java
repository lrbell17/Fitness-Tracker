package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

public class WorkoutNotFoundException extends RuntimeException {

    public WorkoutNotFoundException(final String workoutId) {
        super(String.format(ResponseMessage.WORKOUT_ID_NOT_FOUND, workoutId));
    }
}
