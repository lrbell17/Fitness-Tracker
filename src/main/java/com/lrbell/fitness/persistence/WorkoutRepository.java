package com.lrbell.fitness.persistence;

import com.lrbell.fitness.model.Workout;
import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, String> {
}
