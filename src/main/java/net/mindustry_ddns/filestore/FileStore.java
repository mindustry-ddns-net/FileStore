package net.mindustry_ddns.filestore;

import java.io.*;

/**
 * A {@code FileStore} provides easier management of io operations with objects.
 *
 * @param <T> the stored object type
 */
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
     * @return the file where the object is stored
     */
    File getFile();

    /**
     * Set the file where the object is stored.
     *
     * @param path the path of the file
     */
    void setFile(String path);

    /**
     * Save the stored object to the file.
     * The parent directories are created if needed.
     */
    void save();

    /**
     * Load the stored object from the file.
     * If the file doesn't exist, it is created, with its parent directories if needed.
     */
    void load();
}
