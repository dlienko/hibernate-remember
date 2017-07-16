package com.github.dlienko.yoga.controller;

import static com.github.dlienko.util.Streams.streamOf;
import static com.github.dlienko.yoga.converter.CustomConversionService.conversionFunction;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.converter.CustomConversionService;
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ImageRepository;

@RestController
@RequestMapping(
        path = "/v1/images-meta",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ImageMetaController {

    private final ImageRepository imageRepository;
    private final CustomConversionService conversionService;

    @Autowired
    public ImageMetaController(
            ImageRepository imageRepository,
            CustomConversionService conversionService) {
        this.imageRepository = imageRepository;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<ImageMeta> getAll() {
        return streamOf(imageRepository.findAll())
                .map(conversionFunction(conversionService, ImageMeta.class))
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public ImageMeta getById(@PathVariable("id") UUID id) {
        ImageEntity image = imageRepository.findOne(id);
        return conversionService.toImageMeta(image);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        imageRepository.delete(id);
    }

}
