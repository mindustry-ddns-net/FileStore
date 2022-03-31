package net.mindustry_ddns.filestore;

import net.mindustry_ddns.filestore.exception.SyntaxException;
import net.mindustry_ddns.filestore.serial.Serializer;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

final class SimpleFileStore<T> implements FileStore<T> {

    private File file;
    private final Serializer<T> serializer;
    private final Type type;
    private T object;

    SimpleFileStore(String path, Serializer<T> serializer, Type type, T object) {
        this.file = new File(path);
        this.serializer = serializer;
        this.type = type;
        this.object = object;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(String path) {
        file = new File(path);
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
            try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)){
                object = serializer.deserialize(reader, type);
            } catch (IOException | SyntaxException e) {
                throw new RuntimeException("Unable to load the file store at " + getFile(), e);
            }
        }
    }

    @Override
    public Type getType() {
        return type;
    }
}
