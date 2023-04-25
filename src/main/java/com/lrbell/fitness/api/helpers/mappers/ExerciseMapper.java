package com.lrbell.fitness.api.helpers.mappers;

import com.lrbell.fitness.api.helpers.dto.ExerciseDto;
import com.lrbell.fitness.model.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExerciseMapper extends GenericMapper<Exercise, ExerciseDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ExerciseDto dto, @MappingTarget Exercise entity);
}
