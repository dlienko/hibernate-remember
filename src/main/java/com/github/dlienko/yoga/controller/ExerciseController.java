package com.github.dlienko.yoga.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dlienko.yoga.controller.payload.CreateExercise;
import com.github.dlienko.yoga.model.Exercise;
import com.github.dlienko.yoga.repository.ExerciseRepository;

@RestController
@RequestMapping("/v1/exercises")
public class ExerciseController {

    private final Logger log = getLogger(lookup().lookupClass());

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Iterable<Exercise> getAll() {
        return exerciseRepository.findAll();
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Exercise getById(@PathVariable(value = "id") Long id) {
        return exerciseRepository.findOne(id);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Exercise create(@RequestBody CreateExercise request) {
        Exercise entity = Exercise.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        log.info("Inserting entity: {}", entity);

        return exerciseRepository.save(entity);
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        exerciseRepository.delete(id);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Void> handleError(EmptyResultDataAccessException e) {
        log.warn("No resource found", e);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
