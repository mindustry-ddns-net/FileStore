package net.mindustry_ddns.filestore;

import java.lang.reflect.Type;

public interface Store<T> {

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
     * Save the stored object to the file.
     * The parent directories are created if needed.
     */
    void save();

    /**
     * Load the stored object from the file.
     * If the file doesn't exist, it is created, with its parent directories if needed.
     */
    void load();

    Type getType();
}
