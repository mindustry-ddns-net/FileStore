package net.mindustry_ddns.filestore.serial;

import com.google.gson.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.*;

final class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson;

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    public GsonSerializer() {
        this(new Gson());
    }

    @Override
    public T deserialize(Reader reader, Type type) throws IOException {
        try {
            return gson.fromJson(reader, type);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new IOException("An exception occurred while reading json.", e);
        }
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        try {
            gson.toJson(object, writer);
        } catch (JsonIOException e) {
            throw new IOException("An exception occurred while writing json.", e);
        }
    }
}
