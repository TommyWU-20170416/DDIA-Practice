package com.example.ch1avro;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;

    static Runnable unchecked(ThrowingRunnable throwingRunnable) {
        return () -> {
            try {
                throwingRunnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}