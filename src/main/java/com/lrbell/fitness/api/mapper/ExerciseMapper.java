package com.lrbell.fitness.api.mapper;

import com.lrbell.fitness.api.dto.ExerciseDto;
import com.lrbell.fitness.model.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExerciseMapper extends GenericMapper<Exercise, ExerciseDto> {

    @Override
    ExerciseDto entityToDto(Exercise exercise);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(ExerciseDto dto, @MappingTarget Exercise entity);
}
