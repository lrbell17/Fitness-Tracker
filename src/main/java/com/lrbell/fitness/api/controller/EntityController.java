package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.AbstractResponseBody;
import com.lrbell.fitness.model.ModelEntity;
import org.springframework.http.ResponseEntity;

public interface EntityController<T extends ModelEntity, U extends GenericDto> {
    public ResponseEntity<GenericDto> getById(final String id);

    public ResponseEntity<AbstractResponseBody> create(final T entity);

    public ResponseEntity<AbstractResponseBody> delete(final String id);
}
