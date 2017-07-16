package com.github.dlienko.yoga.controller.payload;

import java.util.UUID;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class CreateImage {

    @JsonProperty("id")
    UUID imageId;

}
