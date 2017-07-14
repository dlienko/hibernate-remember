package com.github.dlienko.yoga.controller;

import static com.github.dlienko.util.Streams.streamOf;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.converter.ImageEntityToMetaConverter;
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ImageRepository;

@RestController
@RequestMapping(
        path = "/v1/images-meta",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ImageMetaController {

    private final ImageRepository imageRepository;
    private final ImageEntityToMetaConverter imageConverter;

    @Autowired
    public ImageMetaController(
            ImageRepository imageRepository,
            ImageEntityToMetaConverter imageConverter) {
        this.imageRepository = imageRepository;
        this.imageConverter = imageConverter;
    }

    @GetMapping
    public List<ImageMeta> getAll() {
        return streamOf(imageRepository.findAll())
                .map(ImageEntityToMetaConverter::convertInLambda)
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public ImageMeta getById(@PathVariable("id")UUID id) {
        ImageEntity image = imageRepository.findOne(id);
        return imageConverter.convert(image);
    }

}
