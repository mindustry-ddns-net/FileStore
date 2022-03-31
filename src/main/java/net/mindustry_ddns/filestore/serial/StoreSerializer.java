package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Factory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Properties;

public interface StoreSerializer<T> {

    static <T> StoreSerializer<T> gson() {
        return new GsonSerializer<>();
    }

    static <T> StoreSerializer<T> gson(Gson gson) {
        return new GsonSerializer<>(gson);
    }

    static <T> StoreSerializer<T> jackson() {
        return new JacksonSerializer<>(new JsonMapper());
    }

    static <T> StoreSerializer<T> jackson(ObjectMapper mapper) {
        return new JacksonSerializer<>(mapper);
    }

    static <T extends Accessible> StoreSerializer<T> config() {
        return new ConfigSerializer<>();
    }

    static <T extends Accessible> StoreSerializer<T> config(Factory factory) {
        return new ConfigSerializer<>(factory);
    }

    static StoreSerializer<Properties> properties() {
        return PropertiesSerializer.INSTANCE;
    }

    T read(Reader reader, Type type) throws IOException;

    void write(Writer writer, T object) throws IOException;
}
