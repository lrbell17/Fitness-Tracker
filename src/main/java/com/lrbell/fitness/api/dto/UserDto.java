package com.lrbell.fitness.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements GenericDto {

    private String userId;
    private String userName;
    private String fullName;
    private String gender;
    private long createdAt;
    private long updatedAt;
}
