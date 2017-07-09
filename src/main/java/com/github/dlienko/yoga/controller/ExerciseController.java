package com.github.dlienko.yoga.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dlienko.yoga.controller.payload.CreateExercise;
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.repository.ExerciseRepository;

@RestController
@RequestMapping(
        path = "/v1/exercises",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ExerciseController {

    private final Logger log = getLogger(lookup().lookupClass());

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping
    public Iterable<ExerciseEntity> getAll() {
        return exerciseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ExerciseEntity getById(@PathVariable(value = "id") Long id) {
        return exerciseRepository.findOne(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public ExerciseEntity create(@RequestBody CreateExercise request) {
        ExerciseEntity entity = ExerciseEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        log.info("Inserting entity: {}", entity);

        return exerciseRepository.save(entity);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
    public ExerciseEntity update(@RequestBody CreateExercise request, @PathVariable(value = "id") Long id) {
        ExerciseEntity entity = ExerciseEntity.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        log.info("Updating entity: {}", entity);

        return exerciseRepository.save(entity);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        exerciseRepository.delete(id);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Void> handleError(EmptyResultDataAccessException e) {
        log.warn("No resource found", e);
        return new ResponseEntity<>(NOT_FOUND);
    }

}
