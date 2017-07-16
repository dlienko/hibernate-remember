package com.github.dlienko.yoga.controller.payload;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateExercise {

    @NotNull
    private String name;

    @NotNull
    private String description;

    private List<CreateImage> images;

}
