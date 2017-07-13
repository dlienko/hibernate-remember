package com.github.dlienko.util;

import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
        //empty
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
