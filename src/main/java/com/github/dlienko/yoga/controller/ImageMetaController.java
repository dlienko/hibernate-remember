package com.github.dlienko.yoga.controller;

import static com.github.dlienko.util.Streams.streamOf;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dlienko.yoga.controller.payload.ImageMeta;
import com.github.dlienko.yoga.repository.ImageRepository;

@RestController
@RequestMapping(
        path = "/v1/images-meta",
        produces = APPLICATION_JSON_UTF8_VALUE)
public class ImageMetaController {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageMetaController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping
    public List<ImageMeta> getAll() {
        return streamOf(imageRepository.findAll())
                .map(image -> ImageMeta.of(image.getId()))
                .collect(toList());
    }

}
