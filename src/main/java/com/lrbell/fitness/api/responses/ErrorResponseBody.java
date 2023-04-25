package com.lrbell.fitness.api.responses;

public class ErrorResponseBody extends AbstractResponseBody {

    public ErrorResponseBody(final String responseMessage, final String id) {
        super(responseMessage, id, Status.ERROR);
    }

    public ErrorResponseBody(final String responseMessage) {
        super(responseMessage, Status.ERROR);
    }
}
