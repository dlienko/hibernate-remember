package com.github.dlienko.yoga.model;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PACKAGE;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = PACKAGE)
@AllArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private byte[] bytes;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "EXERCISE_ID")
    private ExerciseEntity exercise;
}
