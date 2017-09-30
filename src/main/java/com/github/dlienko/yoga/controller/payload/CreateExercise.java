package com.github.dlienko.yoga.controller.payload;

import static com.github.dlienko.yoga.common.Constants.EXERCISE_DESC_LENGTH;
import static com.github.dlienko.yoga.common.Constants.EXERCISE_NAME_LENGTH;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

@Data
public class CreateExercise {

    @NotNull
    @Length(max = EXERCISE_NAME_LENGTH)
    private String name;

    @NotNull
    @Length(max = EXERCISE_DESC_LENGTH)
    private String description;

    private List<CreateImage> images;

}
