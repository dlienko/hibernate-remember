package com.github.dlienko.yoga.controller.payload;

import java.util.UUID;

import lombok.Value;

@Value(staticConstructor = "of")
public class ImageMeta {

    UUID id;

}
