package com.lrbell.fitness.api.responses;

public class ErrorResponseBody extends AbstractResponseBody {

    public ErrorResponseBody(final String responseMessage) {
        super(responseMessage, Status.ERROR);
    }
}
