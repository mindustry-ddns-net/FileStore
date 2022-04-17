package net.mindustry_ddns.filestore;

import io.leangen.geantyref.TypeToken;
import net.mindustry_ddns.filestore.serial.Serializer;

import java.io.File;


/**
 * A {@code FileStore} is a {@code Store} that stores an object on a file.
 *
 * @param <T> the stored object type
 */
public interface FileStore<T> extends Store<T> {

    /**
     * Creates a new {@code FileStore}.
     *
     * @param file       the file
     * @param serializer the serializer instance
     * @param token      the type token of the stored object
     * @param object     the initial value of the store
     * @param <T>        the stored object type
     * @return a new simple {@code FileStore}
     */
    static <T> FileStore<T> of(File file, Serializer<T> serializer, TypeToken<T> token, T object) {
        return new SimpleFileStore<>(file, serializer, token, object);
    }

    /**
     * Creates a new {@code FileStore}.
     *
     * @param path       the path of the file
     * @param serializer the serializer instance
     * @param token      the type token of the stored object
     * @param object     the initial value of the store
     * @param <T>        the stored object type
     * @return a new simple {@code FileStore}
     */
    static <T> FileStore<T> of(String path, Serializer<T> serializer, TypeToken<T> token, T object) {
        return of(new File(path), serializer, token, object);
    }

    /**
     * Creates a new {@code FileStore}.
     * <p>
     * <strong>Attention</strong>, the initial value is null.
     *
     * @param file       the file
     * @param serializer the serializer instance
     * @param token      the type token of the stored object
     * @param <T>        the stored object type
     * @return a new simple {@code FileStore}
     */
    static <T> FileStore<T> of(File file, Serializer<T> serializer, TypeToken<T> token) {
        return of(file, serializer, token, null);
    }

    /**
     * Creates a new {@code FileStore}.
     * <p>
     * <strong>Attention</strong>, the initial value is null.
     *
     * @param path       the path of the file
     * @param serializer the serializer instance
     * @param token      the type token of the stored object
     * @param <T>        the stored object type
     * @return a new simple {@code FileStore}
     */
    static <T> FileStore<T> of(String path, Serializer<T> serializer, TypeToken<T> token) {
        return of(new File(path), serializer, token, null);
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the parent directories of the file don't exist, they are created.
     */
    @Override
    void save();

    /**
     * Returns the file where the object is stored.
     */
    File getFile();

    /**
     * Sets the file where the object is stored.
     *
     * @param file the file
     */
    void setFile(File file);

    /**
     * Sets the file where the object is stored.
     *
     * @param path the path of the file
     */
    default void setFile(String path) {
        setFile(new File(path));
    }

    @Override
    default boolean exists() {
        return getFile().exists();
    }
}
