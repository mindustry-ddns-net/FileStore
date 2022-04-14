package net.mindustry_ddns.filestore.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Factory;

import java.util.Properties;

/**
 * A list of the standard serializers.
 */
public final class Serializers {

    private Serializers() {
    }

    /**
     * Provides a json serializer backed by Gson
     *
     * <blockquote>
     * <strong>Implementation details:</strong>
     * <p>
     * Even if Gson does not need explicit type converters for everything, make sure that at least your object
     * and its fields can be serialized. If it can't, create a {@code TypeAdapter} or {@code TypeAdapterFactory}
     * class that can handle the conversion and register it to the {@code  Gson} instance used by the serializer.
     * </blockquote>
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
     * @param <T>  the type of the store object
     * @return a json serializer backed by Gson, with the specified {@code Gson} instance
     * @see Serializers#gson()
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
        return new JacksonSerializer<>();
    }

    /**
     * Provides a serializer backed by Jackson, whom the serialization format depends on the provided {@code ObjectMapper} instance.
     *
     * @param mapper the {@code ObjectMapper} instance
     * @param <T>    the type of the store object
     * @return a serializer backed by Jackson, with the specified {@code ObjectMapper} instance
     */
    public static <T> Serializer<T> jackson(ObjectMapper mapper) {
        return new JacksonSerializer<>(mapper);
    }

    /**
     * Provides a config serializer backed by Owner.
     * The {@code Accessible} base interface is necessary due to the fact its the only one you can access externally
     *
     * <blockquote>
     * <strong>Implementation details:</strong>
     * <p>
     * To use this store, your {@code Config} interface must extend or implement {@code Accessible}. It should also
     * avoid the usage of the {@code org.aeonbits.owner.Config.Sources} or {@code org.aeonbits.owner.Config.HotReload}
     * annotations since io operations are handled by the store.
     * <p>
     * <strong>Attention</strong>, the library doesn't support generics.
     * </blockquote>
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
     * @param <T>     the type of the store object
     * @return a serializer backed by Owner, with the specified {@code Factory} instance
     * @see Serializers#config()
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
