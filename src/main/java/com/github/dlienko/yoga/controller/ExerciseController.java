package com.github.dlienko.yoga.controller;

import static com.github.dlienko.util.Streams.streamOf;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.stream.Collectors;

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
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ExerciseRepository;
import com.github.dlienko.yoga.repository.ImageRepository;

@RestController
@RequestMapping(
        path = "/v1/exercises",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ExerciseController {

    private final Logger log = getLogger(lookup().lookupClass());

    private final ExerciseRepository exerciseRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ExerciseController(
            ExerciseRepository exerciseRepository,
            ImageRepository imageRepository) {
        this.exerciseRepository = exerciseRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping
    public List<Exercise> getAll() {
        return streamOf(exerciseRepository.findAll())
                .map(Exercise::of)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Exercise getById(@PathVariable(value = "id") Long id) {
        return Exercise.of(exerciseRepository.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public Exercise create(@RequestBody CreateExercise request) {
        ExerciseEntity entity = ExerciseEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Iterable<ImageEntity> images = imageRepository.findAll(request.getImages());

        entity.setImages(streamOf(images).collect(toList()));

        // TODO make transactional

        log.info("Inserting entity: {}", entity.getName());
        ExerciseEntity saved = exerciseRepository.save(entity);

        images.forEach(image -> image.setExercise(saved));
        log.info("Updating images with exercise id {}", saved.getId());
        imageRepository.save(images);


        return Exercise.of(saved);
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
