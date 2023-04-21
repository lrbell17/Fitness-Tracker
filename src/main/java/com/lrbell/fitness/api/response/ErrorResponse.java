package com.lrbell.fitness.api.response;

public class ErrorResponse extends AbstractResponse {

    public ErrorResponse(final String response) {
        super(response, Status.ERROR);
    }

    public ErrorResponse(final String response, final String id) {
        super(String.format(response, id), Status.ERROR);
    }
}
