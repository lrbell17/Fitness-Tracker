package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.Exercise;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise, String> {

    List<Exercise> findByExerciseNameContainingIgnoreCase(final String exerciseName);
}
