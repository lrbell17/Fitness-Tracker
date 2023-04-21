package com.lrbell.fitness.api.dto;

import com.lrbell.fitness.model.enums.MuscleGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseDto implements GenericDto {

    private String exerciseId;
    private String exerciseName;
    private String exerciseDesc;
    private List<MuscleGroup> muscleGroups;
    private long createdAt;
    private long updatedAt;
}
