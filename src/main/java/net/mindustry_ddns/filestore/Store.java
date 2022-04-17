package net.mindustry_ddns.filestore;

import io.leangen.geantyref.TypeToken;
import net.mindustry_ddns.filestore.serial.Serializer;

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
     * Sets the stored object.
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
     * <p>
     * If the store doesn't exist, it is created and the stored object of the store is saved.
     *
     * @see #save()
     */
    void load();

    /**
     * Returns the type token of the stored object.
     */
    TypeToken<T> getTypeToken();

    /**
     * Returns the serializer of the store.
     */
    Serializer<T> getSerializer();

    /**
     * Returns whether the store exists or not.
     */
    boolean exists();
}
