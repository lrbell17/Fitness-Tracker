package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.helpers.dto.WorkoutDto;
import com.lrbell.fitness.api.responses.AbstractResponseBody;
import com.lrbell.fitness.api.responses.OkResponseBody;
import com.lrbell.fitness.api.responses.exceptions.PayloadValidationException;
import com.lrbell.fitness.api.responses.exceptions.UserNotFoundException;
import com.lrbell.fitness.api.responses.exceptions.WorkoutNotFoundException;
import com.lrbell.fitness.api.helpers.mappers.WorkoutMapper;
import com.lrbell.fitness.api.responses.ResponseMessage;
import com.lrbell.fitness.model.User;
import com.lrbell.fitness.model.Workout;
import com.lrbell.fitness.model.enums.WorkoutState;
import com.lrbell.fitness.persistence.QueryHelper;
import com.lrbell.fitness.persistence.UserRepository;
import com.lrbell.fitness.persistence.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The controller exposing endpoints corresponding to the {@link Workout} entity.
 */
@RestController
@RequestMapping("/api/workout")
public class WorkoutController implements UpdatableEntityController<Workout, WorkoutDto> {

    /**
     * The repository for performing database operations on {@link Workout} entities.
     */
    @Autowired
    private WorkoutRepository workoutRepository;

    /**
     * The repository for performing database operations on {@link User} entities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The mapper for mapping {@link WorkoutDto} to {@link Workout}.
     */
    @Autowired
    private WorkoutMapper workoutMapper;

    /**
     * A GET request for fetching the workout by ID.
     *
     * @param id The workout ID.
     * @return A {@link ResponseEntity} representing the workout.
     */
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

    //to-do: handle exercise ID not found error
    /**
     * A POST request to start a workout.
     *
     * @param workout the {@link Workout}.
     * @return A {@link ResponseEntity} response .
     */
    @Override
    @PostMapping("/start")
    public ResponseEntity<AbstractResponseBody> create(@Validated @RequestBody final Workout workout, final BindingResult result) throws PayloadValidationException {

        if (result.hasErrors()) {
            throw new PayloadValidationException(result);
        }

        workout.verifyExercisesExist();
        workout.verifyUserExists();
        workout.setStartTime(System.currentTimeMillis());
        workout.setWorkoutState(WorkoutState.ACTIVE);
        workoutRepository.save(workout);
        return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.WORKOUT_CREATED, workout.getWorkoutId()));
    }

    /**
     * A PUT request to end a workout.
     *
     * @param id The workout ID
     * @return A {@link ResponseEntity} response.
     */
    @PutMapping("/end/{id}")
    public ResponseEntity<AbstractResponseBody> endWorkout(@PathVariable(value = "id") final String id) {
        final Optional<Workout> workout = workoutRepository.findById(id);

        if (workout.isPresent()) {
            final Workout workoutToUpdate = workout.get();
            workoutToUpdate.verifyWorkoutActive();
            workoutToUpdate.setEndTime(System.currentTimeMillis());
            workoutToUpdate.setWorkoutState(WorkoutState.NOT_ACTIVE);
            workoutRepository.save(workoutToUpdate);
            return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.WORKOUT_ENDED, id));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }

    /**
     * A PATCH request to update the workout.
     *
     * @param id The workout ID.
     * @param workoutDto The {@link WorkoutDto} representing the new workout.
     * @return A {@link ResponseEntity} response.
     */
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<AbstractResponseBody> update(@PathVariable(value = "id") final String id,
                                                       @RequestBody final WorkoutDto workoutDto) {
        final Optional<Workout> existingWorkout = workoutRepository.findById(id);

        if (existingWorkout.isPresent()) {
            final Workout workoutToUpdate = existingWorkout.get();
            workoutToUpdate.verifyWorkoutActive();
            workoutToUpdate.verifyUserIdNotUpdated(workoutDto.getUserId());
            workoutToUpdate.verifyExercisesExist();
            workoutMapper.updateFromDto(workoutDto, workoutToUpdate);
            workoutRepository.save(workoutToUpdate);
            return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.WORKOUT_UPDATED, id));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }

    /**
     * A DELETE request to delete a workout.
     *
     * @param id The workout ID.
     * @return A {@link ResponseEntity} response.
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AbstractResponseBody> delete(@PathVariable(value = "id") final String id) {

        if (workoutRepository.findById(id).isPresent()) {
            workoutRepository.deleteById(id);
            return ResponseEntity.ok().body(new OkResponseBody(ResponseMessage.WORKOUT_DELETED, id));
        } else {
            throw new WorkoutNotFoundException(id);
        }
    }

    /**
     * A pageable GET request to list all the workouts for a {@link User}.
     *
     * @param userId the User ID.
     * @param page The page number.
     * @param size The page size.
     * @return A {@link ResponseEntity} containing the list of workouts.
     */
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<WorkoutDto>> listWorkoutsForUser(@PathVariable(value = "userId") final String userId,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size) {
        final Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            final Integer pageNumber = page != null ? page: QueryHelper.DEFAULT_PAGE;
            final Integer pageSize = size != null ? size: QueryHelper.DEFAULT_PAGE_SIZE;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final List<Workout> workouts = workoutRepository.findByUserOrderByStartTimeDesc(user.get(), pageable);
            final List<WorkoutDto> workoutDtos = new ArrayList<>();

            for (Workout workout : workouts) {
                workoutDtos.add(workoutMapper.entityToDto(workout));
            }
            return ResponseEntity.ok().body(workoutDtos);
        } else {
            throw new UserNotFoundException(userId);
        }
    }
}
