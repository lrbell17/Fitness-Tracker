package com.lrbell.fitness.api.responses;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lrbell.fitness.api.responses.exceptions.ExerciseNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.InvalidUserIdUpdateException;
import com.lrbell.fitness.api.responses.exceptions.InvalidWorkoutStateException;
import com.lrbell.fitness.api.responses.exceptions.UserNameNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.UserNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.WorkoutNotFoundException;
import com.lrbell.fitness.model.User;
import com.lrbell.fitness.model.enums.Gender;
import com.lrbell.fitness.model.enums.MuscleGroup;
import jakarta.persistence.Table;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponseBody(ResponseMessage.USER_ID_NOT_FOUND, ex.getUserId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<Object> handleUserNameNotFoundException(final UserNameNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponseBody(ResponseMessage.USER_NAME_NOT_FOUND, ex.getUserName()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<Object> handleExerciseNotFoundException(final ExerciseNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponseBody(ResponseMessage.EXERCISE_ID_NOT_FOUND, ex.getExerciseId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    public ResponseEntity<Object> handleWorkoutNotFoundException(final WorkoutNotFoundException ex) {

        return new ResponseEntity<>(
                new ErrorResponseBody(ResponseMessage.WORKOUT_ID_NOT_FOUND, ex.getWorkoutId()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidWorkoutStateException.class)
    public ResponseEntity<Object> handleInvalidWorkoutStateException(final InvalidWorkoutStateException ex) {

        return ResponseEntity.badRequest().body(
                new ErrorResponseBody(ResponseMessage.INVALID_WORKOUT_STATE, ex.getWorkoutId())
        );
    }

    @ExceptionHandler(InvalidUserIdUpdateException.class)
    public ResponseEntity<Object> handleInvalidUserIdUpdateException(final InvalidUserIdUpdateException ex) {

        return ResponseEntity.badRequest().body(
                new ErrorResponseBody(ResponseMessage.INVALID_USER_ID_UPDATE)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AbstractResponseBody> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException) {
            final InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            final String invalidValue = cause.getValue().toString();

            if (cause.getTargetType().isEnum() && cause.getTargetType().equals(Gender.class)) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponseBody(ResponseMessage.INVALID_GENDER + ": " + invalidValue)
                );
            }
            if (cause.getTargetType().isEnum() && cause.getTargetType().equals(MuscleGroup.class)) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponseBody(ResponseMessage.INVALID_MUSCLE_GROUP + ": " + invalidValue)
                );
            }
        }
        throw ex;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AbstractResponseBody> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        final Throwable cause = ex.getCause();
        final String userTableConstraint = User.class.getAnnotation(Table.class).uniqueConstraints()[0].name().toLowerCase();

        if (cause instanceof ConstraintViolationException) {
            final String constraintName = ((ConstraintViolationException) cause).getConstraintName();

            if (userTableConstraint.equals(constraintName)) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponseBody(ResponseMessage.NON_UNIQUE_USERNAME)
                );
            }
        }
        throw ex;
    }
}
