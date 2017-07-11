package com.github.dlienko.yoga.controller.payload;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateExercise {

    @NotNull
    String name;

    @NotNull
    String description;

    List<UUID> images;

}
