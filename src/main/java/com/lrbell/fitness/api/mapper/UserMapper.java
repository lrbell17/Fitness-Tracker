package com.lrbell.fitness.api.mapper;

import com.lrbell.fitness.api.dto.UserDto;
import com.lrbell.fitness.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto> {

    @Override
    UserDto entityToDto(User user);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(UserDto dto, @MappingTarget User entity);

}
