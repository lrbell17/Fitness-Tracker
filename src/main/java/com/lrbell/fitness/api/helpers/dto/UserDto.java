package com.lrbell.fitness.api.helpers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements GenericDto {

    private String userId;
    private String userName;
    private String fullName;
    private String gender;
    private Long createdAt;
    private Long updatedAt;
}
