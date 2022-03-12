package net.mindustry_ddns.filestore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;


/**
 * A json based store backed by Gson.
 *
 * <blockquote>
 * <strong>Implementation details:</strong>
 * <p>
 * Even if Gson does not need explicit type converters for everything, make sure that at least your object
 * and its fields can be serialized. If it can't, create a {@link TypeAdapter} or {@link TypeAdapterFactory}
 * class that can handle the conversion and register it to the {@link Gson} instance used by the store.
 * </blockquote>
 *
 * @param <T> the stored object type
 */
public class JsonFileStore<T> extends AbstractFileStore<T> {

    private final Type type;
    private final Gson gson;

    /**
     * Base constructor of the {@code JsonFileStore}.
     *
     * @param path     the path of the file
     * @param type     the type of the stored object
     * @param supplier the supplier for the initial value of the file store
     * @param gson     the gson instance
     */
    public JsonFileStore(String path, Type type, Supplier<T> supplier, Gson gson) {
        super(new File(path), supplier);
        this.type = type;
        this.gson = gson;
    }

    /**
     * This constructor creates a gson instance with pretty printing enabled.
     *
     * @see JsonFileStore#JsonFileStore(String, Type, Supplier, Gson)
     */
    public JsonFileStore(String path, Type type, Supplier<T> supplier) {
        this(path, type, supplier, new GsonBuilder().setPrettyPrinting().create());
    }

    /**
     * Static constructor that calls {@link #load()} directly after the {@code JsonFileStore} creation.
     *
     * @see JsonFileStore#JsonFileStore(String, Type, Supplier, Gson)
     */
    public static <T> JsonFileStore<T> load(String path, Type type, Supplier<T> supplier, Gson gson) {
        JsonFileStore<T> store = new JsonFileStore<>(path, type, supplier, gson);
        store.load();
        return store;
    }

    /**
     * Static constructor that calls {@link #load()} directly after the {@code JsonFileStore} creation.
     *
     * @see JsonFileStore#JsonFileStore(String, Type, Supplier)
     */
    public static <T> JsonFileStore<T> load(String path, Type type, Supplier<T> supplier) {
        JsonFileStore<T> store = new JsonFileStore<>(path, type, supplier);
        store.load();
        return store;
    }

    @Override
    protected void saveImpl() throws IOException {
        try (Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            gson.toJson(get(), type, writer);
        }
    }

    @Override
    protected void loadImpl() throws IOException {
        try (Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
            set(gson.fromJson(reader, type));
        }
    }

    /**
     * @return the gson instance
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * @return the type of the stored object
     */
    public Type getType() {
        return type;
    }
}
