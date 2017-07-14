package com.github.dlienko.yoga.converter;

import org.springframework.core.convert.converter.Converter;

import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.model.ImageEntity;

public class ImageEntityToMetaConverter implements Converter<ImageEntity, ImageMeta> {

    @Override
    public ImageMeta convert(ImageEntity imageEntity) {
        return ImageMeta.builder()
                .imageId(imageEntity.getId())
                .name(imageEntity.getName())
                .withLinks()
                .build();
    }

}
