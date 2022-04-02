package net.mindustry_ddns.filestore.serial;

import io.leangen.geantyref.*;
import java.io.*;
import java.lang.reflect.*;
import net.mindustry_ddns.filestore.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

public class JsonSerializerTest {

    static final Type TEST_OBJECT_TYPE = new TypeToken<TestObject<Long>>(){}.getType();
    static final String TEST_OBJECT_JSON_STRING = """
        {
            "number": 3
        }
        """;

    @ParameterizedTest
    @ValueSource(strings = {"GSON", "JACKSON"})
    void test_serialization(String name) throws IOException {
        TestObject<Long> object = new TestObject<>();
        object.setNumber(7L);
        Serializer<TestObject<Long>> serializer = getSerializer(name);

        String json;

        try (StringWriter writer = new StringWriter()) {
            serializer.serialize(writer, object);
            json = writer.toString();
        }

        try (Reader reader = new StringReader(json)) {
            TestObject<Long> out = serializer.deserialize(reader, TEST_OBJECT_TYPE);

            Assertions.assertInstanceOf(Long.class, out.getNumber());
            Assertions.assertEquals(out.getNumber(), 7L);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"GSON", "JACKSON"})
    void test_deserialization(String name) throws IOException {
        try (Reader reader = new StringReader(TEST_OBJECT_JSON_STRING)) {
            Serializer<TestObject<Long>> serializer = getSerializer(name);
            TestObject<Long> out = serializer.deserialize(reader, TEST_OBJECT_TYPE);

            Assertions.assertInstanceOf(Long.class, out.getNumber());
            Assertions.assertEquals(out.getNumber(), 3L);
        }
    }

    <T> Serializer<T> getSerializer(String name) {
        return switch (name) {
            case "GSON" -> Serializers.gson();
            case "JACKSON" -> Serializers.jackson();
            default -> throw new IllegalArgumentException();
        };
    }
}
