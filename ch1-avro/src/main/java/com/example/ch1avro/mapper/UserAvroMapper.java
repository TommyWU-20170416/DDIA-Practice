package com.example.ch1avro.mapper;

import java.io.ByteArrayOutputStream;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;

import com.example.ch1avro.AvroMixin;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;

import user.avro.UserAvro;

public class UserAvroMapper {

    private static DatumReader<UserAvro> reader = new SpecificDatumReader<>(UserAvro.class);
    private static DatumWriter<UserAvro> writer = new SpecificDatumWriter<>(UserAvro.class);
    /**
     * 使用 DatumReader 實現 deserialize
     * @param data
     * @return
     * @throws Exception
     */
    public static UserAvro avroDeserializeUser(byte[] data) throws Exception {
        Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
        return reader.read(null, decoder);
    }

    /**
     * 使用 DatumWriter 實現 serialize
     * @param user
     * @return
     * @throws Exception
     */
    public static byte[] avroSerializeUser(UserAvro user) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        writer.write(user, encoder);
        encoder.flush();
        outputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * 使用 jackson 的套件
     */
    private static final AvroMapper mapper = new AvroMapper();
    private static final AvroSchema schema;

    static {
        try {
            // 由於 avro 會產生 specificData 屬性，但 schema 中沒有，因此需要忽略掉
            mapper.addMixIn(SpecificRecordBase.class, AvroMixin.class);

            schema = new AvroSchema(UserAvro.getClassSchema());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Avro schema", e);
        }
    }

    public static byte[] jacksonSerialize(UserAvro user) throws Exception {
        return mapper.writer(schema).writeValueAsBytes(user);
    }

    public static UserAvro jacksonDeserialize(byte[] data) throws Exception {
        return mapper.readerFor(UserAvro.class).with(schema).readValue(data);
    }
}
