package com.github.dlienko.yoga.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.dlienko.yoga.controller.payload.ErrorMessage;
import com.github.dlienko.yoga.controller.payload.ErrorMessage.ErrorCode;
import com.github.dlienko.yoga.converter.CustomConversionService;
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ImageRepository;

@Controller
@RequestMapping("/v1/images")
public class ImageUploadController {

    private final Logger log = getLogger(lookup().lookupClass());

    private final ImageRepository imageRepository;
    private final CustomConversionService conversionService;

    @Autowired
    public ImageUploadController(
            ImageRepository imageRepository,
            CustomConversionService conversionService) {
        this.imageRepository = imageRepository;
        this.conversionService = conversionService;
    }

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file) {
        log.debug("Single file upload!");

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ErrorMessage.builder()
                            .code(ErrorCode.EMPTY_FILE)
                            .message("File is empty")
                            .build());
        }

        try {
            ImageEntity savedImage = saveUploadedFile(file);
            return ResponseEntity.ok()
                    .body(conversionService.toImageMeta(savedImage));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorMessage.builder()
                            .code(ErrorCode.UPLOAD_ERROR)
                            .message("Failed to read the image")
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") UUID id) {
        ImageEntity file = imageRepository.findOne(id);

        return ResponseEntity.ok()
                // TODO determine and store content type on upload
                .contentType(MediaType.valueOf("image/jpg"))
                .body(file.getBytes());
    }

    private ImageEntity saveUploadedFile(MultipartFile file) throws IOException {
        UUID id = randomUUID();
        return imageRepository.save(ImageEntity.builder()
                .id(id)
                .name(file.getOriginalFilename())
                .bytes(file.getBytes())
                .build());
    }

}
