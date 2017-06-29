package com.github.dlienko.yoga.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

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
import com.github.dlienko.yoga.model.ImageEntity;
import com.github.dlienko.yoga.repository.ImageRepository;

@Controller
@RequestMapping("/v1/images")
public class ImageUploadController {

    private final Logger logger = getLogger(lookup().lookupClass());

    private final ImageRepository imageRepository;

    @Autowired
    public ImageUploadController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file) {
        logger.debug("Single file upload!");

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ErrorMessage.builder()
                            .code(ErrorCode.EMPTY_FILE)
                            .message("File is empty")
                            .build());
        }

        try {
            List<UUID> ids = saveUploadedFiles(singletonList(file));
            // TODO return self-descriptive image
            return ResponseEntity.ok()
                    .body(singletonMap("files", ids));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorMessage.builder()
                            .code(ErrorCode.UPLOAD_ERROR)
                            .message("Failed to read the image")
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") UUID id, HttpServletResponse response) {
        ImageEntity file = imageRepository.findOne(id);

        return ResponseEntity.ok()
                // TODO determine and store content type on upload
                .contentType(MediaType.valueOf("image/png"))
                .body(file.getBytes());
    }

    private List<UUID> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        // TODO do it with streams, write some nice utils
        List<UUID> ids = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            UUID id = randomUUID();
            imageRepository.save(ImageEntity.builder()
                    .id(id)
                    .name(file.getOriginalFilename())
                    .bytes(file.getBytes())
                    .build());
            ids.add(id);
        }

        return ids;
    }
}
