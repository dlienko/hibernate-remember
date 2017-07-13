package com.github.dlienko.yoga.service;

import static com.github.dlienko.util.Predicates.not;
import static com.github.dlienko.util.Streams.streamOf;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Set;
import java.util.UUID;

import lombok.experimental.Delegate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dlienko.yoga.controller.payload.CreateExercise;
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ExerciseRepository;
import com.github.dlienko.yoga.repository.ImageRepository;

@Component
public class ExerciseService {

    private final Logger log = getLogger(lookup().lookupClass());

    @Delegate(types = Delegated.class)
    private final ExerciseRepository exerciseRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ExerciseService(
            ExerciseRepository exerciseRepository,
            ImageRepository imageRepository) {
        this.exerciseRepository = exerciseRepository;
        this.imageRepository = imageRepository;
    }

    @SuppressWarnings("unused")
    private interface Delegated {
        Iterable<ExerciseEntity> findAll();
        ExerciseEntity findOne(long id);
        void delete(long id);
    }

    public ExerciseEntity createExercise(CreateExercise request) {
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
        return saved;
    }

    public ExerciseEntity updateExercise(CreateExercise request, long id) {
        ExerciseEntity entity = ExerciseEntity.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Iterable<ImageEntity> newImages = imageRepository.findAll(request.getImages());

        Set<UUID> newImageIds = streamOf(newImages).map(ImageEntity::getId).collect(toSet());

        ExerciseEntity oldExercise = exerciseRepository.findOne(id);

        Set<ImageEntity> imagesToRemove = oldExercise.getImages().stream()
                .filter(not(image -> newImageIds.contains(image.getId())))
                .collect(toSet());
        imageRepository.delete(imagesToRemove);

        entity.setImages(streamOf(newImages).collect(toList()));

        // TODO make transactional

        log.info("Updating entity: {}", entity.getName());
        ExerciseEntity saved = exerciseRepository.save(entity);

        newImages.forEach(image -> image.setExercise(saved));
        log.info("Updating images with exercise id {}", saved.getId());
        imageRepository.save(newImages);
        return saved;
    }

}
