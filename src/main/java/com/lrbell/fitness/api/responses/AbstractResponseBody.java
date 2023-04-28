package com.lrbell.fitness.api.responses;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class AbstractResponseBody implements GenericDto {
    private String responseMessage;
    private Status status;
}
