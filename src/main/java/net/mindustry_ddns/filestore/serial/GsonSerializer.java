package net.mindustry_ddns.filestore.serial;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.mindustry_ddns.filestore.exception.SyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

final class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson;

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    public GsonSerializer() {
        this(new Gson());
    }

    @Override
    public T deserialize(Reader reader, Type type) throws IOException, SyntaxException {
        try {
            return gson.fromJson(reader, type);
        } catch (JsonIOException e) {
            throw new IOException(e);
        } catch (JsonSyntaxException e) {
            throw new SyntaxException(e);
        }
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        try {
            gson.toJson(object, writer);
        } catch (JsonIOException e) {
            throw new IOException(e);
        }
    }
}
