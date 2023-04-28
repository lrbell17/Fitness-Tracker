package com.lrbell.fitness.model;

import com.lrbell.fitness.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
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
@Table(name = "USERS",
        indexes = @Index(columnList = "userName"),
        uniqueConstraints = @UniqueConstraint(name = "uk_userName", columnNames = "userName"))
public class User implements ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @Column
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    private Long createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Long updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Workout> workouts;
}
