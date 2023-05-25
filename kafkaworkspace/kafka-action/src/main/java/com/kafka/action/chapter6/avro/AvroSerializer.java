package com.kafka.action.chapter6.avro;

/**
 * Created by 17081290 on 2021/7/2.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 *
 * Description: avro序列化器<br/>
 *
 * @author moudaen
 * @version 1.0
 */
public class AvroSerializer<T extends SpecificRecordBase> implements
        Serializer<T> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    /**
     * 实现序列化方法
     */
    @Override
    public byte[] serialize(String topic, T data) {
        if (null == data) {
            return null;
        }
        DatumWriter<T> writer = new SpecificDatumWriter<>(data.getSchema());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(
                outputStream, null);
        try {
            writer.write(data, binaryEncoder);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
        return outputStream.toByteArray();
    }

    @Override
    public void close() {

    }
}