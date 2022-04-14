package net.mindustry_ddns.filestore;

import io.leangen.geantyref.TypeToken;
import net.mindustry_ddns.filestore.serial.Serializer;

import java.io.*;
import java.nio.charset.StandardCharsets;

final class SimpleFileStore<T> implements FileStore<T> {

    private final Serializer<T> serializer;
    private final TypeToken<T> type;
    private File file;
    private T object;

    SimpleFileStore(File file, Serializer<T> serializer, TypeToken<T> type, T object) {
        this.file = file;
        this.serializer = serializer;
        this.type = type;
        this.object = object;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public T get() {
        return object;
    }

    @Override
    public void set(T object) {
        this.object = object;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void save() {
        // Creates parent directories
        getFile().getAbsoluteFile().getParentFile().mkdirs();

        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            serializer.serialize(writer, object);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the file store at " + getFile(), e);
        }
    }

    @Override
    public void load() {
        if (!getFile().exists()) {
            save();
        } else {
            try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                object = serializer.deserialize(reader, type);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load the file store at " + getFile(), e);
            }
        }
    }

    @Override
    public TypeToken<T> getTypeToken() {
        return type;
    }

    @Override
    public Serializer<T> getSerializer() {
        return serializer;
    }
}
