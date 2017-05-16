package com.github.dlienko.yoga.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.dlienko.yoga.model.Exercise;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

}
