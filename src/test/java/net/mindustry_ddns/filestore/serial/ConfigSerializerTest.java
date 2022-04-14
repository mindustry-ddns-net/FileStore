package net.mindustry_ddns.filestore.serial;

import io.leangen.geantyref.TypeToken;
import net.mindustry_ddns.filestore.util.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class ConfigSerializerTest {

    static final TypeToken<TestConfig> TEST_CONFIG_TYPE = new TypeToken<>() {};
    static final String TEST_CONFIG_PROPERTIES_STRING = """
            test.number = 3
            """;

    @Test
    void test_serialization() throws IOException {
        TestConfig object = ConfigFactory.create(TestConfig.class);
        Serializer<TestConfig> serializer = Serializers.config();

        String properties;

        try (StringWriter writer = new StringWriter()) {
            serializer.serialize(writer, object);
            properties = writer.toString();
        }

        try (Reader reader = new StringReader(properties)) {
            TestConfig out = serializer.deserialize(reader, TEST_CONFIG_TYPE);

            Assertions.assertEquals(out.getNumber(), 7L);
        }
    }

    @Test
    void test_deserialization() throws IOException {
        try (StringReader reader = new StringReader(TEST_CONFIG_PROPERTIES_STRING)) {
            Serializer<TestConfig> serializer = Serializers.config();
            TestConfig out = serializer.deserialize(reader, TEST_CONFIG_TYPE);

            Assertions.assertEquals(out.getNumber(), 3L);
        }
    }
}
