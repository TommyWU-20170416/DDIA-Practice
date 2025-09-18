package com.example.ch1avro;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> {
    void accept(T t) throws Exception;

    static <T> Consumer<T> unchecked(ThrowingConsumer<T> throwingConsumer) {
        return t -> {
            try {
                throwingConsumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}