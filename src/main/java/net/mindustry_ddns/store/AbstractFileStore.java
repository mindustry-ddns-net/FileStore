package net.mindustry_ddns.store;

import java.io.*;
import java.util.function.*;


/**
 * Base class for specific implementations of a file store.
 *
 * @param <T> the stored object type
 */
public abstract class AbstractFileStore<T> implements FileStore<T> {
    private File file;
    private T object;

    protected AbstractFileStore(File file, Supplier<T> supplier) {
        this.file = file;
        this.object = supplier.get();
    }

    @Override
    public T get() {
        return object;
    }

    @Override
    public void set(T object) {
        this.object = object;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(String path) {
        this.file = new File(path);
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
