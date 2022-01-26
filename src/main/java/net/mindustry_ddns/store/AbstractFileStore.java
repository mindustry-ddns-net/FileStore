package net.mindustry_ddns.store;

import java.io.*;
import java.util.function.*;


public abstract class AbstractFileStore<T> implements FileStore<T> {
    private final Class<T> clazz;
    private File file;
    private T object;

    public AbstractFileStore(File file, Class<T> clazz, Supplier<T> supplier) {
        this.file = file;
        this.clazz = clazz;
        this.object = supplier.get();
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
    public void reload() {
        this.object = load();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    protected abstract T load();

    @Override
    public String toString() {
        return object.toString();
    }
}
