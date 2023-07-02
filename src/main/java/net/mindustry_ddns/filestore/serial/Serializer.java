package net.mindustry_ddns.filestore.serial;

import io.leangen.geantyref.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * A {@code Serializer} reads and writes an object for a {@link net.mindustry_ddns.filestore.Store}.
 *
 * @param <T> the type of the store object
 */
public interface Serializer<T> {

    T deserialize(Reader reader, TypeToken<T> token) throws IOException;

    void serialize(Writer writer, T object) throws IOException;
}
