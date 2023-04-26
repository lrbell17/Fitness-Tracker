package com.lrbell.fitness.api.helpers.dto;

import com.lrbell.fitness.model.Exercise;
import com.lrbell.fitness.model.enums.WorkoutState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutDto implements GenericDto {

    private String workoutId;
    private String workoutName;
    private String userId;
    private WorkoutState workoutState;
    private List<Exercise> exercises;
    private Long startTime;
    private Long endTime;
}
