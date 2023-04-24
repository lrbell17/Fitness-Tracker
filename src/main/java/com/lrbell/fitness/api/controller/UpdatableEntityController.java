package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.AbstractResponse;
import com.lrbell.fitness.model.ModelEntity;
import org.springframework.http.ResponseEntity;

public interface UpdatableEntityController<T extends ModelEntity, U extends GenericDto> extends EntityController<T, U> {

    public ResponseEntity<AbstractResponse> update(final String id, final U entityDto);

}
