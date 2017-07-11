package com.github.dlienko.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Streams {

    private Streams() {
        //empty
    }

    public static <T> Stream<T> streamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}
