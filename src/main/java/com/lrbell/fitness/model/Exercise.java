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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EXERCISE",
        indexes = @Index(columnList = "exerciseName"),
        uniqueConstraints = @UniqueConstraint(name = "uk_exerciseName", columnNames = "exerciseName"))
public class Exercise implements ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String exerciseId;

    @Column
    @NotNull
    private String exerciseName;

    @Column
    private String exerciseDesc;

    @Column
    @Enumerated(EnumType.STRING)
    @OrderBy("muscleGroups ASC")
    @ElementCollection(targetClass = MuscleGroup.class)
    private List<MuscleGroup> muscleGroups;

    @Temporal(TemporalType.TIMESTAMP)
    private Long createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Long updatedAt;
}
