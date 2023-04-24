package com.lrbell.fitness.api.helpers.mappers;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.model.ModelEntity;

public interface GenericMapper<T extends ModelEntity, U extends GenericDto> {

    U entityToDto(T entity);

    void updateFromDto(U dto, T entity);

}
