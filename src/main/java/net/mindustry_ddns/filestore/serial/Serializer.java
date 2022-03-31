package net.mindustry_ddns.filestore.serial;

import net.mindustry_ddns.filestore.exception.SyntaxException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public interface Serializer<T> {

    T deserialize(Reader reader, Type type) throws IOException, SyntaxException;

    void serialize(Writer writer, T object) throws IOException;
}
