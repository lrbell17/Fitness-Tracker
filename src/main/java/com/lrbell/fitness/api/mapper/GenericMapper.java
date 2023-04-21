package com.lrbell.fitness.api.mapper;

import com.lrbell.fitness.api.dto.GenericDto;
import com.lrbell.fitness.model.ModelEntity;

public interface GenericMapper<T extends ModelEntity, U extends GenericDto> {

    U entityToDto(T entity);

    void updateFromDto(U dto, T entity);

}
