package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Factory;

public class Serializers {

    public static <T> GsonSerializer<T> getGsonSerializer() {
        return new GsonSerializer<>();
    }

    public static <T> GsonSerializer<T> getGsonSerializer(Gson gson) {
        return new GsonSerializer<>(gson);
    }

    public static <T> JacksonSerializer<T> getJacksonSerializer() {
        return new JacksonSerializer<>(new JsonMapper());
    }

    public static <T> JacksonSerializer<T> getJacksonSerializer(ObjectMapper mapper) {
        return new JacksonSerializer<>(mapper);
    }

    public static <T extends Accessible> ConfigSerializer<T> getConfigSerializer(Class<T> type) {
        return new ConfigSerializer<>(type);
    }

    public static <T extends Accessible> ConfigSerializer<T> getConfigSerializer(Class<T> type, Factory factory) {
        return new ConfigSerializer<>(type, factory);
    }

    public static PropertiesSerializer getPropertiesSerializer() {
        return PropertiesSerializer.INSTANCE;
    }
}
