package net.mindustry_ddns.store;

import java.io.*;


public interface FileStore<T> {
    T get();

    Class<T> getObjectClass();

    void set(T object);

    void save();

    void reload();

    File getFile();

    void setFile(File file);
}
