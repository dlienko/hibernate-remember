package com.github.dlienko.yoga.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.github.dlienko.yoga.controller.payload.Exercise;
import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.model.ExerciseEntity;
import com.github.dlienko.yoga.model.ImageEntity;

@Service
public class CustomConversionService {

    private final ConversionService conversionService;

    @Autowired
    public CustomConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public Exercise toExercise(ExerciseEntity exerciseEntity) {
        return conversionService.convert(exerciseEntity, Exercise.class);
    }

    public ImageMeta toImageMeta(ImageEntity imageEntity) {
        return conversionService.convert(imageEntity, ImageMeta.class);
    }

    public static <A, B> Function<A, B> conversionFunction(CustomConversionService conversionService, Class<B> bClass) {
        return (A source) -> conversionService.conversionService.convert(source, bClass);
    }

    static <A, B> Function<A, B> conversionFunction(Converter<A, B> converter) {
        return converter::convert;
    }

}
