package com.lrbell.fitness.model;

import com.lrbell.fitness.model.enums.MuscleGroup;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXERCISES", indexes = @Index(columnList = "exerciseName"))
public class Exercise implements ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String exerciseId;

    @Column(unique = true, nullable = false)
    private String exerciseName;

    @Column
    private String exerciseDesc;

    @Column
    @Enumerated(EnumType.STRING)
    @OrderBy("muscleGroups ASC")
    @ElementCollection(targetClass = MuscleGroup.class)
    private List<MuscleGroup> muscleGroups;

    @Temporal(TemporalType.TIMESTAMP)
    private long createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private long updatedAt;
}
