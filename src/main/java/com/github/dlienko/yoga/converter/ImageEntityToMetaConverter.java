package com.github.dlienko.yoga.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.model.ImageEntity;

@Component
public class ImageEntityToMetaConverter implements Converter<ImageEntity, ImageMeta> {

    @Override
    public ImageMeta convert(ImageEntity imageEntity) {
        return convertInLambda(imageEntity);
    }

    public static ImageMeta convertInLambda(ImageEntity imageEntity) {
        return ImageMeta.builder()
                .imageId(imageEntity.getId())
                .name(imageEntity.getName())
                .withLinks()
                .build();
    }

}
