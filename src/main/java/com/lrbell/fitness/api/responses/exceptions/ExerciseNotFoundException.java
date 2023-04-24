package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;
import lombok.Getter;

@Getter
public class ExerciseNotFoundException extends RuntimeException {

    private final String exerciseId;

    public ExerciseNotFoundException(final String exerciseId) {
        super(String.format(ResponseMessage.EXERCISE_ID_NOT_FOUND, exerciseId));
        this.exerciseId = exerciseId;
    }
}
