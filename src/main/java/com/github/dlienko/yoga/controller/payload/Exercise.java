package com.github.dlienko.yoga.controller.payload;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import lombok.Value;

import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.model.ImageEntity;

@Value
public class Exercise {

    long id;
    String name;
    String description;
    List<UUID> images;

    public static Exercise of(ExerciseEntity entity) {
        if (entity == null) {
            return null;
        }

        List<UUID> images = entity.getImages().stream()
                .map(ImageEntity::getId)
                .collect(toList());

        return new Exercise(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                images
        );
    }

}
