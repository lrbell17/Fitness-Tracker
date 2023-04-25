package com.lrbell.fitness.api.helpers.mappers;

import com.lrbell.fitness.api.helpers.dto.UserDto;
import com.lrbell.fitness.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserDto dto, @MappingTarget User entity);

}
