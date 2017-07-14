package com.github.dlienko.yoga.controller.payload;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dlienko.yoga.controller.ImageUploadController;

@Value
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ImageMeta extends ResourceSupport {

    @JsonProperty("id")
    UUID imageId;

    String name;

    @SuppressWarnings("unused")
    public static class ImageMetaBuilder {
        private boolean generateLinks = false;

        public ImageMetaBuilder withLinks() {
            generateLinks = true;
            return this;
        }

        public ImageMeta build() {
            ImageMeta imageMeta = new ImageMeta(imageId, name);

            if (generateLinks) {
                imageMeta.add(
                        linkTo(methodOn(ImageUploadController.class)
                                .downloadFile(imageId))
                                .withSelfRel());
            }

            return imageMeta;
        }
    }

}
