package com.lrbell.fitness.api.controller;

import com.lrbell.fitness.api.helpers.dto.ExerciseDto;
import com.lrbell.fitness.api.helpers.dto.GenericDto;
import com.lrbell.fitness.api.responses.exceptions.ExerciseNotFoundException;
import com.lrbell.fitness.api.helpers.mappers.ExerciseMapper;
import com.lrbell.fitness.api.responses.AbstractResponse;
import com.lrbell.fitness.api.responses.OkResponse;
import com.lrbell.fitness.api.responses.ResponseMessage;
import com.lrbell.fitness.model.Exercise;
import com.lrbell.fitness.persistence.ExerciseRepository;
import com.lrbell.fitness.persistence.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            throw new ExerciseNotFoundException(id);
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
            throw new ExerciseNotFoundException(id);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AbstractResponse> delete(@PathVariable(value= "id") final String id) {
        if (exerciseRepository.findById(id).isPresent()) {
            exerciseRepository.deleteById(id);
            return ResponseEntity.ok().body(new OkResponse(ResponseMessage.EXERCISE_DELETED, id));
        } else {
            throw new ExerciseNotFoundException(id);
        }
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> query(@RequestParam final String name,
                                                   @RequestParam(required = false) final Integer page,
                                                   @RequestParam(required = false) final Integer size) {

        final Integer pageNumber = page != null ? page: QueryHelper.DEFAULT_PAGE;
        final Integer pageSize = size != null ? size: QueryHelper.DEFAULT_PAGE_SIZE;
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final List<Exercise> exercises = exerciseRepository.findByExerciseNameContainingIgnoreCase(name, pageable);
        final List <ExerciseDto> exerciseDtos = new ArrayList<>();

        for (final Exercise exercise : exercises) {
            exerciseDtos.add(exerciseMapper.entityToDto(exercise));
        }
        return ResponseEntity.ok().body(exerciseDtos);
    }
}
