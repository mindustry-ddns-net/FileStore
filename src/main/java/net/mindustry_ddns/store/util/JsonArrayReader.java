package net.mindustry_ddns.store.util;

import com.google.gson.*;
import com.google.gson.stream.*;
import net.mindustry_ddns.store.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.util.*;


/**
 * An iterator backed by a {@link JsonReader} to read the elements of a json array.
 *
 * @param <E> the element type
 */
public class JsonArrayReader<E> implements Iterator<E>, Closeable{
    private final JsonReader reader;
    private final Type type;
    private final Gson gson;
    private boolean closed = false;

    /**
     * Create a {@code JsonArrayReader} that reads a json array from a reader.
     *
     * @param reader the reader reading the json array
     * @param type   the element type
     * @param gson   the gson instance
     */
    public JsonArrayReader(Reader reader, Type type, Gson gson) {
        try {
            this.reader = new JsonReader(reader);
            this.type = type;
            this.gson = gson;
            this.reader.beginArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the object.", e);
        }
    }

    /**
     * @see JsonArrayReader#JsonArrayReader(Reader, Type, Gson)
     */
    public JsonArrayReader(Reader reader, Type type) {
        this(reader, type, new Gson());
    }

    /**
     * Create a {@code JsonArrayReader} that reads a json array from a file.
     *
     * @param path the path of the file where the json array is located
     * @param type   the element type
     * @param gson   the gson instance
     */
    public JsonArrayReader(String path, Type type, Gson gson) {
        try (Reader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            this.reader = new JsonReader(reader);
            this.type = type;
            this.gson = gson;
            this.reader.beginArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the object at " + path, e);
        }
    }

    /**
     * @see JsonArrayReader#JsonArrayReader(String, Type, Gson)
     */
    public JsonArrayReader(String path, Type type) {
        this(path, type, new Gson());
    }

    /**
     * Create a {@code JsonArrayReader} that reads a json array from a file store.
     *
     * @param store the file store
     * @param type  the element type
     */
    public JsonArrayReader(JsonFileStore<?> store, Type type) {
        this(store.getFile().getPath(), type, store.getGson());
    }

    /**
     * @return true if there are elements to read
     */
    @Override
    public boolean hasNext() {
        try {
            if (!closed && reader.hasNext()) {
                return true;
            } else {
                if (!closed)
                    reader.close();
                closed = true;
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse and return the next element of the json array.
     *
     * @return the next element
     * @throws NoSuchElementException if there is no more element to read
     */
    @Override
    public E next() {
        if (!closed) {
            return gson.fromJson(reader, type);
        } else {
            throw new NoSuchElementException("No more elements to read.");
        }
    }

    @Override
    public void close() throws IOException {
        closed = true;
        reader.close();
    }
}
