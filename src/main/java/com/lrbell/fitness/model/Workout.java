package com.lrbell.fitness.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lrbell.fitness.api.helpers.UserDeserializer;
import com.lrbell.fitness.api.responses.exceptions.InvalidWorkoutStateException;
import com.lrbell.fitness.model.enums.WorkoutState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WORKOUTS", indexes = @Index(columnList = "userId"))
public class Workout implements ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String workoutId;

    @Column
    private String workoutName;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonDeserialize(using = UserDeserializer.class)
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkoutState workoutState;

    @Temporal(TemporalType.TIMESTAMP)
    private Long startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Long endTime;

    // TO-DO
//    @OneToMany
//    @JoinColumn(name = "workoutExerciseId")
//    private WorkoutExercise workoutExercise;

    public void verifyWorkoutActive() {
        if (this.workoutState != WorkoutState.ACTIVE) {
            throw new InvalidWorkoutStateException();
        }
    }
}
