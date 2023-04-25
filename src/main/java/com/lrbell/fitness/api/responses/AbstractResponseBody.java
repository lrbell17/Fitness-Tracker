package com.lrbell.fitness.api.responses;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractResponseBody implements GenericDto {
    private String responseMessage;
    private String id;
    private Status status;

    public AbstractResponseBody(final String responseMessage, final Status status) {
        this.responseMessage = responseMessage;
        this.status = status;
    }
}
