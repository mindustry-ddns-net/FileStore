package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.leangen.geantyref.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

final class JacksonSerializer<T> implements Serializer<T> {

    private final ObjectMapper mapper;

    JacksonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    JacksonSerializer() {
        this.mapper = new JsonMapper();
    }

    @Override
    public T deserialize(Reader reader, TypeToken<T> token) throws IOException {
        return mapper.readValue(reader, mapper.getTypeFactory().constructType(token.getType()));
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        mapper.writeValue(writer, object);
    }
}
