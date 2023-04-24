package com.lrbell.fitness.api.responses;

import com.lrbell.fitness.api.responses.exceptions.ExerciseNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.InvalidWorkoutStateException;
import com.lrbell.fitness.api.responses.exceptions.UserNameNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.UserNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.WorkoutNotFoundException;
import com.lrbell.fitness.api.responses.ErrorResponse;
import com.lrbell.fitness.api.responses.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(ResponseMessage.USER_ID_NOT_FOUND, ex.getUserId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<Object> handleUserNameNotFoundException(final UserNameNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(ResponseMessage.USER_NAME_NOT_FOUND, ex.getUserName()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<Object> handleExerciseNotFoundException(final ExerciseNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(ResponseMessage.EXERCISE_ID_NOT_FOUND, ex.getExerciseId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    public ResponseEntity<Object> handleWorkoutNotFoundException(final WorkoutNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponse(ResponseMessage.WORKOUT_ID_NOT_FOUND, ex.getWorkoutId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidWorkoutStateException.class)
    public ResponseEntity<Object> handleInvalidWorkoutStateException(final InvalidWorkoutStateException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(ResponseMessage.INVALID_WORKOUT_STATE)
        );
    }
}
