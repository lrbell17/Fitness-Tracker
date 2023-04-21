package com.lrbell.fitness.api.response;

import com.lrbell.fitness.api.dto.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractResponse implements GenericDto {
    private String responseMessage;
    private Status status;
}
