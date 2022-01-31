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
public class JsonArrayFileReader<E> implements Iterator<E> {
    private final JsonReader reader;
    private final Type type;
    private final Gson gson;
    private boolean closed = false;

    /**
     * @param file the file to read
     * @param type the element type
     * @param gson the gson instance
     */
    public JsonArrayFileReader(File file, Type type, Gson gson) {
        try (final Reader fileReader = new FileReader(file, StandardCharsets.UTF_8)) {
            this.reader = new JsonReader(fileReader);
            this.type = type;
            this.gson = gson;
            this.reader.beginArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the object at " + file, e);
        }
    }

    /**
     * @param file the file to read
     * @param type the element type
     */
    public JsonArrayFileReader(File file, Type type) {
        this(file, type, new Gson());
    }

    /**
     * @param store the file store
     * @param type  the element type
     */
    public JsonArrayFileReader(JsonFileStore<?> store, Type type) {
        this(store.getFile(), type, store.getGson());
    }

    /**
     * @return true if there are elements to read
     */
    @Override
    public boolean hasNext() {
        try {
            if (reader.hasNext()) {
                return true;
            } else {
                if (!closed)
                    reader.close();
                closed = true;
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException();
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
            throw new NoSuchElementException();
        }
    }
}
