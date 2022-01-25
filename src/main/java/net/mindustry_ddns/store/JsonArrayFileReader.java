package net.mindustry_ddns.store;

import com.google.gson.*;
import com.google.gson.stream.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;


public class JsonArrayFileReader<E> implements Iterator<E> {
    private final JsonReader reader;
    private final Class<E> clazz;
    private final Gson gson;
    private boolean closed = false;

    public JsonArrayFileReader(File file, Class<E> clazz, Gson gson) {
        try {
            this.reader = new JsonReader(new FileReader(file, StandardCharsets.UTF_8));
            this.clazz = clazz;
            this.gson = gson;
            reader.beginArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the object at " + file, e);
        }
    }

    public JsonArrayFileReader(File file, Class<E> clazz) {
        this(file, clazz, new Gson());
    }

    public JsonArrayFileReader(JsonFileStore<?> store, Class<E> clazz) {
        this(store.getFile(), clazz, store.getGson());
    }

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

    @Override
    public E next() {
        if (!closed) {
            return gson.fromJson(reader, clazz);
        } else {
            throw new NoSuchElementException();
        }
    }
}
