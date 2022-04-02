package net.mindustry_ddns.filestore;

import java.lang.reflect.Type;
import net.mindustry_ddns.filestore.serial.*;

/**
 * A {@code Store} is a simple utility class that can load, store and save an object, making persistence very easy to handle.
 *
 * @param <T> the type of the store object
 */
public interface Store<T> {

    /**
     * Returns the stored object.
     */
    T get();

    /**
     * Set the stored object.
     *
     * @param object the object to store
     */
    void set(T object);

    /**
     * Saves the stored object.
     */
    void save();

    /**
     * Loads the stored object.
     */
    void load();

    /**
     * Returns the type of the stored object.
     */
    Type getType();

    /**
     * Returns the serializer of the store.
     */
    Serializer<T> getSerializer();
}
