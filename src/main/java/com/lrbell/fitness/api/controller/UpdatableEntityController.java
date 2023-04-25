package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.AbstractResponseBody;
import com.lrbell.fitness.model.ModelEntity;
import org.springframework.http.ResponseEntity;

public interface UpdatableEntityController<T extends ModelEntity, U extends GenericDto> extends EntityController<T, U> {

    public ResponseEntity<AbstractResponseBody> update(final String id, final U entityDto);

}
