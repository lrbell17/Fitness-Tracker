package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.helpers.dto.WorkoutDto;
import com.lrbell.fitness.api.responses.exceptions.WorkoutNotFoundException;
import com.lrbell.fitness.api.helpers.mappers.WorkoutMapper;
import com.lrbell.fitness.api.responses.AbstractResponse;
import com.lrbell.fitness.api.responses.OkResponse;
import com.lrbell.fitness.api.responses.ResponseMessage;
import com.lrbell.fitness.model.Workout;
import com.lrbell.fitness.model.enums.WorkoutState;
import com.lrbell.fitness.persistence.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController implements EntityController<Workout, WorkoutDto> {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutMapper workoutMapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenericDto> getById(@PathVariable(value = "id") final String id) {
        final Optional<Workout> workout = workoutRepository.findById(id);

        if (workout.isPresent()) {
            return ResponseEntity.ok().body(workoutMapper.entityToDto(workout.get()));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }

    @Override
    @PostMapping("/start")
    public ResponseEntity<AbstractResponse> create(@RequestBody final Workout workout) {
        workout.setStartTime(System.currentTimeMillis());
        workout.setWorkoutState(WorkoutState.ACTIVE);
        workoutRepository.save(workout);
        return ResponseEntity.ok().body(new OkResponse(ResponseMessage.WORKOUT_CREATED, workout.getWorkoutId()));
    }

    @PutMapping("/end/{id}")
    public ResponseEntity<AbstractResponse> endWorkout(@PathVariable(value = "id") final String id) {
        final Optional<Workout> workout = workoutRepository.findById(id);

        if (workout.isPresent()) {
            final Workout workoutToUpdate = workout.get();
            workoutToUpdate.verifyWorkoutActive();
            workoutToUpdate.setEndTime(System.currentTimeMillis());
            workoutToUpdate.setWorkoutState(WorkoutState.NOT_ACTIVE);
            workoutRepository.save(workoutToUpdate);
            return ResponseEntity.ok().body(new OkResponse(ResponseMessage.WORKOUT_ENDED, id));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AbstractResponse> delete(@PathVariable(value = "id") final String id) {

        if (workoutRepository.findById(id).isPresent()) {
            workoutRepository.deleteById(id);
            return ResponseEntity.ok().body(new OkResponse(ResponseMessage.WORKOUT_DELETED, id));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }
}
