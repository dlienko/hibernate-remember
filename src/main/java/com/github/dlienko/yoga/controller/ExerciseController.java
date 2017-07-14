package com.github.dlienko.yoga.controller;

import static com.github.dlienko.util.Streams.streamOf;
import static com.github.dlienko.yoga.converter.CustomConversionService.conversionFunction;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

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
import com.github.dlienko.yoga.controller.payload.Exercise;
import com.github.dlienko.yoga.converter.CustomConversionService;
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.service.ExerciseService;

@RestController
@RequestMapping(
        path = "/v1/exercises",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ExerciseController {

    private final Logger log = getLogger(lookup().lookupClass());

    private final ExerciseService exerciseService;
    private final CustomConversionService conversionService;

    @Autowired
    public ExerciseController(
            ExerciseService exerciseService,
            CustomConversionService conversionService) {
        this.exerciseService = exerciseService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<Exercise> findAll() {
        return streamOf(exerciseService.findAll())
                .map(conversionFunction(conversionService, Exercise.class))
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public Exercise findById(@PathVariable(value = "id") long id) {
        return conversionService.toExercise(exerciseService.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public Exercise create(@RequestBody CreateExercise request) {
        ExerciseEntity exercise = exerciseService.createExercise(request);
        return conversionService.toExercise(exercise);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
    public Exercise update(@RequestBody CreateExercise request, @PathVariable(value = "id") long id) {
        ExerciseEntity exercise = exerciseService.updateExercise(request, id);
        return conversionService.toExercise(exercise);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        exerciseService.delete(id);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Void> handleError(EmptyResultDataAccessException e) {
        log.warn("No resource found", e);
        return new ResponseEntity<>(NOT_FOUND);
    }

}
