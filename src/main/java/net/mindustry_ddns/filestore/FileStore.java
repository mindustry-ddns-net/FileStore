package net.mindustry_ddns.filestore;

import net.mindustry_ddns.filestore.serial.Serializer;

import java.io.File;
import java.lang.reflect.Type;


/**
 * A {@code FileStore} is a {@code Store} that stores an object on a file.
 *
 * @param <T> the stored object type
 */
public interface FileStore<T> extends Store<T> {

    static <T> FileStore<T> of(String path, Serializer<T> serializer, Type type, T object) {
        return new SimpleFileStore<>(path, serializer, type, object);
    }

    static <T> FileStore<T> of(String path, Serializer<T> serializer, Type type) {
        return new SimpleFileStore<>(path, serializer, type, null);
    }

    /**
     * Returns the file where the object is stored.
     */
    File getFile();

    /**
     * Set the file where the object is stored.
     *
     * @param path the path of the file
     */
    void setFile(String path);
}
