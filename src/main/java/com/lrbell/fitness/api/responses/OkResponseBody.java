package com.lrbell.fitness.api.responses;

public class OkResponseBody extends AbstractResponseBody {

    public OkResponseBody(final String responseMessage, final String id) {
        super(responseMessage, id, Status.SUCCESS);
    }

    public OkResponseBody(final String responseMessage) {
        super(responseMessage, Status.SUCCESS);
    }
}
