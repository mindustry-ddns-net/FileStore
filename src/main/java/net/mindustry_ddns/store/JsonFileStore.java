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

    public Gson getGson() {
        return gson;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        getFile().getParentFile().mkdirs();

        try (final Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            gson.toJson(get(), getObjectClass(), writer);
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
            set(gson.fromJson(reader, getObjectClass()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the object at " + getFile(), e);
        }
    }
}
