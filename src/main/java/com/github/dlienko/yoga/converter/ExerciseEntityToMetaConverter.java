package com.github.dlienko.yoga.converter;

import static com.github.dlienko.yoga.converter.CustomConversionService.conversionFunction;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.github.dlienko.yoga.controller.payload.Exercise;
import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.model.ImageEntity;

public class ExerciseEntityToMetaConverter implements Converter<ExerciseEntity, Exercise> {

    private final Converter<ImageEntity, ImageMeta> imageConverter;

    public ExerciseEntityToMetaConverter(Converter<ImageEntity, ImageMeta> imageConverter) {
        this.imageConverter = imageConverter;
    }

    @Override
    public Exercise convert(ExerciseEntity exerciseEntity) {
        if (exerciseEntity == null) {
            return null;
        }

        List<ImageMeta> images = exerciseEntity.getImages().stream()
                .map(conversionFunction(imageConverter))
                .collect(toList());

        return new Exercise(
                exerciseEntity.getId(),
                exerciseEntity.getName(),
                exerciseEntity.getDescription(),
                images
        );
    }

}
