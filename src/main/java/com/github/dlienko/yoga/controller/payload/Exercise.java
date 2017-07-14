package com.github.dlienko.yoga.controller.payload;

import java.util.List;

import lombok.Value;

@Value
public class Exercise {

    long id;
    String name;
    String description;
    List<ImageMeta> images;

}
