package net.mindustry_ddns.store;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.util.function.*;


/**
 * A json based store backed by Gson. Even if Gson does not need explicit type converters for everything,
 * make sure that at least your object and the fields of your object can be serialized.
 * If it can't, create a {@link TypeAdapter} or {@link TypeAdapterFactory} that can handle the conversion and register it to the {@link Gson} instance.
 *
 * @param <T> the object type
 */
public class JsonFileStore<T> extends AbstractFileStore<T> {
    private final Type type;
    private final Gson gson;

    public JsonFileStore(File file, Type type, Supplier<T> supplier, Gson gson) {
        super(file, supplier);
        this.type = type;
        this.gson = gson;
        load();
    }

    public JsonFileStore(File file, Type type, Supplier<T> supplier) {
        this(file, type, supplier, new GsonBuilder().setPrettyPrinting().create());
    }

    public Gson getGson() {
        return gson;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        getFile().getAbsoluteFile().getParentFile().mkdirs();

        try (final Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            gson.toJson(get(), type, writer);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the object at " + getFile(), e);
        }
    }

    @Override
    public void load() {
        if (!getFile().exists()) {
            save();
            return;
        }

        try (final Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
            set(gson.fromJson(reader, type));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the object at " + getFile(), e);
        }
    }
}
