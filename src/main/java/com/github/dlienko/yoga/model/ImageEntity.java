package com.github.dlienko.yoga.model;

import static lombok.AccessLevel.PACKAGE;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = PACKAGE)
@AllArgsConstructor
@Entity
public class ImageEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private byte[] bytes;

}
