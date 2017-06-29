package com.github.dlienko.yoga.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.github.dlienko.yoga.model.ImageEntity;

public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {

}
