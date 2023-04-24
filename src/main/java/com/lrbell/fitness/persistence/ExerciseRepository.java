package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.Exercise;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, String> {

    List<Exercise> findByExerciseNameContainingIgnoreCase(final String exerciseName, final Pageable pageable);
}
