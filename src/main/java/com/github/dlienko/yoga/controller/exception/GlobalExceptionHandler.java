package com.github.dlienko.yoga.controller.exception;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.github.dlienko.yoga.controller.payload.ErrorMessage;
import com.github.dlienko.yoga.controller.payload.ErrorMessage.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = getLogger(lookup().lookupClass());

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorMessage> handleError(MultipartException e) {
        log.warn("Image was too large", e);
        return ResponseEntity.badRequest()
                .body(ErrorMessage.builder()
                        .code(ErrorCode.UPLOAD_FILE_TOO_LARGE)
                        .message("File size was too large")
                        .build());
    }

}
