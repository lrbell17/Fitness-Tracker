package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.User;
import com.lrbell.fitness.model.Workout;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for database operations on {@link Workout} entities.
 */
@Repository
public interface WorkoutRepository extends CrudRepository<Workout, String> {

    /**
     * Lists all the workouts for a given user.
     *
     * @param user The {@link User}.
     * @param pageable The {@link Pageable} representing the page number and size.
     * @return
     */
    List<Workout> findByUserOrderByStartTimeDesc(final User user, final Pageable pageable);
}
