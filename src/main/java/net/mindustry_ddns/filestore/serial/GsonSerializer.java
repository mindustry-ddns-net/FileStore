package net.mindustry_ddns.filestore.serial;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

final class GsonSerializer<T> implements StoreSerializer<T> {

    private final Gson gson;

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    public GsonSerializer() {
        this(new Gson());
    }

    @Override
    public T read(Reader reader, Type type) throws IOException {
        try {
            return gson.fromJson(reader, type);
        } catch (JsonParseException e) {
            throw new IOException("An exception occurred while reading json.", e);
        }
    }

    @Override
    public void write(Writer writer, T object) throws IOException {
        try {
            gson.toJson(object, writer);
        } catch (JsonParseException e) {
            throw new IOException("An exception occurred while writing json.", e);
        }
    }
}
