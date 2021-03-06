package com.github.dlienko.yoga.model;

import static com.github.dlienko.yoga.common.Constants.EXERCISE_DESC_LENGTH;
import static com.github.dlienko.yoga.common.Constants.EXERCISE_NAME_LENGTH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PACKAGE;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = PACKAGE)
@AllArgsConstructor
@Entity
@Table(name = "exercises")
public class ExerciseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = EXERCISE_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = EXERCISE_DESC_LENGTH)
    private String description;

    // TODO ??? is it possible to fetch images in bulk when exercises are got in bulk?
    @OneToMany(fetch = LAZY, mappedBy = "exercise", cascade = REMOVE)
    private List<ImageEntity> images;

}
