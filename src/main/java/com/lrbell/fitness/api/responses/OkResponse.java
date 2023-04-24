package com.lrbell.fitness.api.responses;

public class OkResponse extends AbstractResponse {

    public OkResponse(final String response) {
        super(response, Status.SUCCESS);
    }

    public OkResponse(final String response, final String id) {
        super(String.format(response, id), Status.SUCCESS);
    }
}
