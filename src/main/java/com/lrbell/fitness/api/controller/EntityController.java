package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.AbstractResponse;
import com.lrbell.fitness.model.ModelEntity;
import org.springframework.http.ResponseEntity;

public interface EntityController<T extends ModelEntity, U extends GenericDto> {
    public ResponseEntity<GenericDto> getById(final String id);

    public ResponseEntity<AbstractResponse> create(final T entity);

    public ResponseEntity<AbstractResponse> delete(final String id);
}
