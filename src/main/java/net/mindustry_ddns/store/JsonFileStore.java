package net.mindustry_ddns.store;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.*;

public class JsonFileStore<T> implements FileStore<T> {
    private final Class<T> clazz;
    private final Gson gson;
    private File file;
    private T object;

    public JsonFileStore(File file, Class<T> clazz, Gson gson) {
        this.file = file;
        this.clazz = clazz;
        this.gson = gson;
    }

    public JsonFileStore(File file, Class<T> clazz) {
        this(file, clazz, new Gson());
    }

    public Gson getGson() {
        return gson;
    }

    @Override
    public T get() {
        return object;
    }

    @Override
    public Class<T> getObjectClass() {
        return clazz;
    }

    @Override
    public void set(T object) {
        this.object = object;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        file.getAbsoluteFile().getParentFile().mkdirs();

        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            gson.toJson(object, clazz, writer);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the object at " + file, e);
        }
    }

    @Override
    public void load() {
        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            this.object = gson.fromJson(reader, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the object at " + file, e);
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }
}
