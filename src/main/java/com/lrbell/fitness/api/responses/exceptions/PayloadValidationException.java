package com.lrbell.fitness.api.responses.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayloadValidationException extends RuntimeException {

    public PayloadValidationException(final BindingResult result) {
        super(getErrorMessage(result));
    }

    private static String getErrorMessage(BindingResult result) {

        final Map<String, String> errorMap = new HashMap<>();
        final List<FieldError> errors = result.getFieldErrors();

        for (FieldError error : errors) {
            errorMap.put(error.getField(), error.getCode());
        }
        return errorMap.toString();
    }
}
