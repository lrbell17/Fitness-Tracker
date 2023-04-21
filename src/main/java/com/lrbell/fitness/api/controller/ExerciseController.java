package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.dto.ExerciseDto;
import com.lrbell.fitness.api.dto.GenericDto;
import com.lrbell.fitness.api.mapper.ExerciseMapper;
import com.lrbell.fitness.api.response.AbstractResponse;
import com.lrbell.fitness.api.response.ErrorResponse;
import com.lrbell.fitness.api.response.OkResponse;
import com.lrbell.fitness.api.response.ResponseMessage;
import com.lrbell.fitness.model.Exercise;
import com.lrbell.fitness.persistence.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController implements UpdatableEntityController<Exercise, ExerciseDto> {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenericDto> getById(@PathVariable(value = "id") final String id) {
        final Optional<Exercise> exercise = exerciseRepository.findById(id);

        if (exercise.isPresent()) {
            return ResponseEntity.ok().body(exerciseMapper.entityToDto(exercise.get()));
        } else {
            return new ResponseEntity<>(
                    new ErrorResponse(ResponseMessage.EXERCISE_ID_NOT_FOUND, id), HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<AbstractResponse> create(@RequestBody final Exercise exercise) {
        exercise.setCreatedAt(System.currentTimeMillis());
        exerciseRepository.save(exercise);
        return ResponseEntity.ok().body(new OkResponse(ResponseMessage.EXERCISE_CREATED, exercise.getExerciseId()));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<AbstractResponse> update(@PathVariable(value = "id") final String id,
                                                   @RequestBody final ExerciseDto exerciseDto) {
        final Optional<Exercise> existingExercise = exerciseRepository.findById(id);

        if (existingExercise.isPresent()) {
            final Exercise exerciseToUpdate = existingExercise.get();
            exerciseToUpdate.setUpdatedAt(System.currentTimeMillis());
            exerciseMapper.updateFromDto(exerciseDto, exerciseToUpdate);
            exerciseRepository.save(exerciseToUpdate);
            return ResponseEntity.ok().body(new OkResponse(ResponseMessage.EXERCISE_UPDATED, id));
        } else {
            return new ResponseEntity<>(
                    new ErrorResponse(ResponseMessage.EXERCISE_ID_NOT_FOUND, id), HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AbstractResponse> delete(@PathVariable(value= "id") final String id) {
        if (exerciseRepository.findById(id).isPresent()) {
            exerciseRepository.deleteById(id);
            return ResponseEntity.ok().body(new OkResponse(ResponseMessage.EXERCISE_DELETED, id));
        } else {
            return new ResponseEntity<>(
                    new ErrorResponse(ResponseMessage.EXERCISE_ID_NOT_FOUND, id), HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> query(@RequestParam final String name) {
        System.out.println(name);
        final List<Exercise> exercises = exerciseRepository.findByExerciseNameContainingIgnoreCase(name);
        final List <ExerciseDto> exerciseDtos = new ArrayList<>();

        for (final Exercise exercise : exercises) {
            System.out.println(exercise.toString());
            exerciseDtos.add(exerciseMapper.entityToDto(exercise));
        }
        return ResponseEntity.ok().body(exerciseDtos);
    }
}
