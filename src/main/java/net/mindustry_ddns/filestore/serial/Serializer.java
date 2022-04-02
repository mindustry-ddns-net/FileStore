package net.mindustry_ddns.filestore.serial;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * A {@code Serializer} reads and writes an object for a {@link net.mindustry_ddns.filestore.Store}.
 *
 * @param <T> the type of the store object
 */
public interface Serializer<T> {

    T deserialize(Reader reader, Type type) throws IOException;

    void serialize(Writer writer, T object) throws IOException;
}
