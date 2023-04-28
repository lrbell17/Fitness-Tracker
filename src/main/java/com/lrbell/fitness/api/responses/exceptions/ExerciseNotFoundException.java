package com.lrbell.fitness.api.responses.exceptions;

import com.lrbell.fitness.api.responses.ResponseMessage;

import java.util.List;

public class ExerciseNotFoundException extends RuntimeException {


    public ExerciseNotFoundException(final String exerciseId) {
        super(String.format(ResponseMessage.EXERCISE_ID_NOT_FOUND, exerciseId));
    }

    public ExerciseNotFoundException(final List<String> exerciseIds) {
        super(String.format((ResponseMessage.EXERCISE_ID_NOT_FOUND), exerciseIds.toString()));
    }
}
