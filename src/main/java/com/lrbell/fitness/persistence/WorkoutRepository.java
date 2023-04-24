package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.User;
import com.lrbell.fitness.model.Workout;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, String> {

    List<Workout> findByUserOrderByStartTimeDesc(final User user, final Pageable pageable);
}
