package com.example.ch1avro.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.springframework.stereotype.Service;

@Service
public class UserAvroService {

    public void changeAvroUserVersion() throws IOException {
        // 1. 讀取舊版 Schema，建立 GenericRecord
        Schema writerSchema = new Schema.Parser().parse(new File("ch1-avro/src/main/avro/v1.avsc"));
        GenericRecord userV1 = new GenericData.Record(writerSchema);
        userV1.put("name", "Alice");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(writerSchema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(userV1, encoder);
        encoder.flush();
        byte[] avroBytes = out.toByteArray(); // 這就是舊資料

        // 2. 使用新版
        Schema readerSchema = new Schema.Parser().parse(new File("ch1-avro/src/main/avro/v2.avsc"));
        DatumReader<GenericRecord> reader = new GenericDatumReader<>(writerSchema, readerSchema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(avroBytes, null);
        GenericRecord userV2 = reader.read(null, decoder);

        System.out.println(userV2.get("name")); // Alice
        System.out.println(userV2.get("age"));  // 0 (default)
    }
}
