package com.github.dlienko.yoga.controller.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateExercise {

    @NotNull
    String name;

    @NotNull
    String description;

}
