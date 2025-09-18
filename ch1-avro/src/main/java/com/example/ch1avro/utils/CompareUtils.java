package com.example.ch1avro.utils;

import com.example.ch1avro.com.example.ch1avro.functional.ThrowingConsumer;
import com.example.ch1avro.com.example.ch1avro.functional.ThrowingRunnable;
import com.example.ch1avro.domain.User;
import com.example.ch1avro.mapper.UserAvroMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import user.avro.UserAvro;

public class CompareUtils {

    private static final String name = "Tommy";
    private static final Integer favoriteNumber = 41;
    private static final String favoriteColor = "yellow";

    private static final UserAvro userAvro = new UserAvro(name, favoriteNumber, favoriteColor);
    private static final User userJson = new User(name, favoriteNumber, favoriteColor);

    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static void compareSizes() throws Exception {
        final byte[] avroBytes = UserAvroMapper.avroSerializeUser(userAvro);
        final byte[] jsonBytes = jsonMapper.writeValueAsBytes(userJson);

        System.out.println("avroBytes.length: " + avroBytes.length + ", jsonBytes.length: " + jsonBytes.length);
    }

    @SneakyThrows
    public static void compareSerializeTime() {
        measureSerializeTime(() -> UserAvroMapper.avroSerializeUser(userAvro), "Avro Origin");
        measureSerializeTime(() -> UserAvroMapper.jacksonSerialize(userAvro), "Avro Jackson");
        measureSerializeTime(() -> jsonMapper.writeValueAsBytes(userJson), "Json");
    }

    private static void measureSerializeTime(ThrowingRunnable serializer, String label) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            try {
                serializer.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(label + " serialize time: " + (end - start) + " ms");
    }

    @SneakyThrows
    public static void compareDeserializeTime() throws Exception {
        final byte[] avroBytes = UserAvroMapper.avroSerializeUser(userAvro);
        final byte[] jsonBytes = jsonMapper.writeValueAsBytes(userJson);

        measureDeserializeTime(avroBytes, UserAvroMapper::avroDeserializeUser, "Avro Origin");
        measureDeserializeTime(avroBytes, UserAvroMapper::jacksonDeserialize, "Avro Jackson");
        measureDeserializeTime(jsonBytes, bytes -> jsonMapper.readValue(bytes, User.class), "Json");
    }

    private static void measureDeserializeTime(byte[] data, ThrowingConsumer<byte[]> deserializer,
                                               String label) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            try {
                deserializer.accept(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(label + " deserialize time: " + (end - start) + " ms");
    }
}
