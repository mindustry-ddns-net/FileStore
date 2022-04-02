package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import java.util.*;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Factory;

/**
 * A list of the standard serializers.
 */
public final class Serializers {

    private Serializers() {
    }

    /**
     * Provides a json serializer backed by Gson
     *
     * @param <T> the type of the store object
     * @return a json serializer backed by Gson
     */
    public static <T> Serializer<T> gson() {
        return new GsonSerializer<>();
    }

    /**
     * Provides a json serializer backed by Gson.
     *
     * @param gson the {@code Gson} instance
     * @param <T> the type of the store object
     * @return a json serializer backed by Gson, with the specified {@code Gson} instance
     */
    public static <T> Serializer<T> gson(Gson gson) {
        return new GsonSerializer<>(gson);
    }

    /**
     * Provides a json serializer backed by Jackson, with its {@code JsonMapper}.
     *
     * @param <T> the type of the store object
     * @return a json serializer backed by Jackson
     */
    public static <T> Serializer<T> jackson() {
        return new JacksonSerializer<>(new JsonMapper());
    }

    /**
     * Provides a serializer backed by Jackson, whom the serialization format depends on the provided {@code ObjectMapper} instance.
     *
     * @param mapper the {@code ObjectMapper} instance
     * @param <T> the type of the store object
     * @return a serializer backed by Jackson, with the specified {@code ObjectMapper} instance
     */
    public static <T> Serializer<T> jackson(ObjectMapper mapper) {
        return new JacksonSerializer<>(mapper);
    }

    /**
     * Provides a config serializer backed by Owner.
     *
     * @param <T> the type of the store object
     * @return a serializer backed by Jackson
     */
    public static <T extends Accessible> Serializer<T> config() {
        return new ConfigSerializer<>();
    }

    /**
     * Provides a config serializer backed by Owner.
     *
     * @param factory the {@code Factory} instance
     * @param <T> the type of the store object
     * @return a serializer backed by Owner, with the specified {@code Factory} instance
     */
    public static <T extends Accessible> Serializer<T> config(Factory factory) {
        return new ConfigSerializer<>(factory);
    }

    /**
     * Provides a properties serializer, using vanilla java.
     *
     * @return a properties serializer
     */
    public static Serializer<Properties> properties() {
        return PropertiesSerializer.INSTANCE;
    }
}
