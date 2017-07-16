package com.github.dlienko.yoga.controller.payload;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import lombok.Builder;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonInclude;

@Value
@Builder
@JsonInclude(NON_NULL)
public class Exercise {

    long id;
    String name;
    String description;
    List<ImageMeta> images;

}
