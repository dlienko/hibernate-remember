package com.github.dlienko.yoga.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.dlienko.yoga.model.ExerciseEntity;

public interface ExerciseRepository extends CrudRepository<ExerciseEntity, Long> {

}
