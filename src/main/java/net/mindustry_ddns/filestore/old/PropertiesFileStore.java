package net.mindustry_ddns.filestore.old;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * A simpler version of {@link ConfigFileStore} using a raw Property object.
 */
public class PropertiesFileStore extends AbstractFileStore<Properties> {

    /**
     * Base constructor of the {@code PropertiesFileStore}.
     *
     * @param path    the path of the file
     * @param imports a list of maps acting as default values, make sure the content only contains strings
     */
    public PropertiesFileStore(String path, Map<?, ?>... imports) {
        super(new File(path), Properties::new);
        for (Map<?, ?> map : imports) get().putAll(map);
    }

    /**
     * Static constructor that calls {@link #load()} directly after the {@code PropertiesFileStore} creation.
     *
     * @see PropertiesFileStore#PropertiesFileStore(String, Map[])
     */
    public static PropertiesFileStore load(String path, Map<?, ?>... imports) {
        PropertiesFileStore store = new PropertiesFileStore(path, imports);
        store.load();
        return store;
    }

    @Override
    protected void saveImpl() throws IOException {
        try (Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            get().store(writer, null);
        }
    }

    @Override
    protected void loadImpl() throws IOException {
        try (Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
            get().clear();
            get().load(reader);
        }
    }

    @Override
    public Type getType() {
        return Properties.class;
    }
}
