package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public class JacksonSerializer<T> implements Serializer<T> {

    private final ObjectMapper mapper;

    public JacksonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public T deserialize(Reader reader, Type type) throws IOException {
        return mapper.readValue(reader, mapper.getTypeFactory().constructType(type));
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        mapper.writeValue(writer, object);
    }
}
