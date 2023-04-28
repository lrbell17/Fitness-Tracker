package com.lrbell.fitness.api.responses;

import lombok.Getter;

@Getter
public class OkResponseBody extends AbstractResponseBody {

    private String id;

    public OkResponseBody(final String responseMessage, final String id) {
        super(responseMessage, Status.SUCCESS);
        this.id = id;
    }

    public OkResponseBody(final String responseMessage) {
        super(responseMessage, Status.SUCCESS);
    }
}
