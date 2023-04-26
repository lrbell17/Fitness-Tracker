package com.lrbell.fitness.api.helpers.mappers;

import com.lrbell.fitness.api.helpers.dto.WorkoutDto;
import com.lrbell.fitness.model.Workout;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WorkoutMapper extends GenericMapper<Workout, WorkoutDto> {

    @Override
    @Mapping(source = "user.userId", target = "userId")
    WorkoutDto entityToDto(final Workout workout);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(WorkoutDto dto, @MappingTarget Workout entity);
}
