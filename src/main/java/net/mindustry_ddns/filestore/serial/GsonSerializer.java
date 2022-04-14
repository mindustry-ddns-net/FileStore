package net.mindustry_ddns.filestore.serial;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.leangen.geantyref.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

final class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson;

    GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    GsonSerializer() {
        this(new Gson());
    }

    @Override
    public T deserialize(Reader reader, TypeToken<T> token) throws IOException {
        try {
            return gson.fromJson(reader, token.getType());
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
