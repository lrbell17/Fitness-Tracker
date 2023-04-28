package com.lrbell.fitness.api.helpers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.lrbell.fitness.model.Exercise;
import com.lrbell.fitness.persistence.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A deserializer for converting a list of exercise IDs from a JSON object to a list of {@link Exercise}s.
 */
public class ExerciseDeserializer extends JsonDeserializer<List<Exercise>> {

    /**
     * Dummy name to mark an exercise as not found.
     */
    public static final String EXERCISE_NOT_FOUND_DUMMY_NAME = "NOT_FOUND";

    /**
     * The repository for performing database operations on {@link Exercise} entities.
     */
    @Autowired
    private ExerciseRepository exerciseRepository;

    /**
     * Converts a list of exercise IDs from the JSON object to a list of {@link Exercise}s.
     *
     * @param jp Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *   this deserialization activity.
     *
     * @return A {@link List} of {@link Exercise}s.
     * @throws IOException
     * @throws JacksonException
     */
    @Override
    public List<Exercise> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        final JsonNode node = jp.getCodec().readTree(jp);
        final List<Exercise> exercises = new ArrayList<>();
        final List<Exercise> notFoundExerciseList = new ArrayList<>();

        for (JsonNode exerciseNode : node) {
            final String exerciseId = exerciseNode.get("exerciseId").asText();
            final Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
            if (exercise.isPresent()) {
                exercises.add(exercise.get());
            } else {
                final Exercise notFoundExercise = new Exercise();
                notFoundExercise.setExerciseId(exerciseId);
                notFoundExercise.setExerciseName(EXERCISE_NOT_FOUND_DUMMY_NAME);
                notFoundExerciseList.add(notFoundExercise);
            }
        }

        if (notFoundExerciseList.size() > 0) {
            return notFoundExerciseList;
        }
        return exercises;
    }


}
