package net.mindustry_ddns.store;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.*;
import java.util.function.*;


public class JsonFileStore<T> extends AbstractFileStore<T> {
    private final Gson gson;

    public JsonFileStore(File file, Class<T> clazz, Supplier<T> supplier, Gson gson) {
        super(file, clazz, supplier);
        this.gson = gson;
    }

    public JsonFileStore(File file, Class<T> clazz, Supplier<T> supplier) {
        this(file, clazz, supplier, new Gson());
    }

    public static <T> JsonFileStore<T> of(File file, Class<T> clazz, Supplier<T> supplier, Gson gson) {
        JsonFileStore<T> store = new JsonFileStore<>(file, clazz, supplier, gson);
        store.reload();
        return store;
    }

    public static <T> JsonFileStore<T> of(File file, Class<T> clazz, Supplier<T> supplier) {
        JsonFileStore<T> store = new JsonFileStore<>(file, clazz, supplier);
        store.reload();
        return store;
    }

    public Gson getGson() {
        return gson;
    }

    @Override
    public void save() {
        try (final Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            gson.toJson(get(), getObjectClass(), writer);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the object at " + getFile(), e);
        }
    }

    @Override
    protected T load() {
        if (!getFile().exists()) return get();

        try (final Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, getObjectClass());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the object at " + getFile(), e);
        }
    }
}
