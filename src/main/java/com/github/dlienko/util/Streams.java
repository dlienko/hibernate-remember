package com.github.dlienko.util;

import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Streams {

    private Streams() {
        //empty
    }

    public static <T> Stream<T> streamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> streamOfNullable(Collection<T> collection) {
        return ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
    }

}
