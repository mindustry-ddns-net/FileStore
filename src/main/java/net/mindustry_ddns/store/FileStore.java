package net.mindustry_ddns.store;

import java.io.*;


public interface FileStore<T> {
    T get();

    Class<T> getObjectClass();

    void set(T object);

    void save();

    void load();

    File getFile();

    void setFile(File file);

    default void setFile(String file) {
        setFile(new File(file));
    }
}
