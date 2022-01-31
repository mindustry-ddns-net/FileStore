package net.mindustry_ddns.store;

import java.io.*;


public interface FileStore<T> {
    /**
     * @return the stored object
     */
    T get();

    /**
     * Set the stored object.
     *
     * @param object the object to store
     */
    void set(T object);

    /**
     * @return the file the object is stored
     */
    File getFile();

    /**
     * Set the file where the stored object is saved.
     *
     * @param file the file to write to
     */
    void setFile(File file);

    /**
     * Save the stored object to the file.
     * The parent directories are created if needed.
     */
    void save();

    /**
     * Load the stored object from the file. If it doesn't exist, the file is created.
     * The parent directories are created if needed.
     */
    void load();
}
