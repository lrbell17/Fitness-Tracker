package com.lrbell.fitness.api.helpers.mappers;

import com.lrbell.fitness.api.helpers.dto.WorkoutDto;
import com.lrbell.fitness.model.User;
import com.lrbell.fitness.model.Workout;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WorkoutMapper extends GenericMapper<Workout, WorkoutDto> {

    @Override
    @Mapping(source = "user.userId", target = "userId")
    WorkoutDto entityToDto(final Workout workout);

    @Named("userIdToUser")
    default User userIdToUser(final String userId) {
        final User user = new User();
        user.setUserId(userId);
        return user;
    }

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    void updateFromDto(WorkoutDto dto, @MappingTarget Workout entity);
}
