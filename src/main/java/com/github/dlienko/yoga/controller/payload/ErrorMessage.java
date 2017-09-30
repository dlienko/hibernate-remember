package com.github.dlienko.yoga.controller.payload;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ErrorMessage {

    @NonNull
    ErrorCode code;
    @NotNull
    String message;

    public enum ErrorCode {
        EMPTY_FILE,
        UPLOAD_ERROR,
        UPLOAD_FILE_TOO_LARGE
    }

}
