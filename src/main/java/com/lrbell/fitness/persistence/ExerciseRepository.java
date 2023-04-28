package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.Exercise;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for database operations on {@link Exercise} entities.
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, String> {

    /**
     * Queries exercises by name.
     *
     * @param exerciseName A String to compare to the exerciseName column.
     * @param pageable A {@link Pageable} representing the page number and size.
     * @return A list of {@link Exercise}s returned by the query
     */
    List<Exercise> findByExerciseNameContainingIgnoreCase(final String exerciseName, final Pageable pageable);
}
