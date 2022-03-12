package net.mindustry_ddns.store;

import net.mindustry_ddns.store.util.*;
import org.aeonbits.owner.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;


/**
 * A property based store backed by the <a href="http://owner.aeonbits.org/">OWNER library</a>.
 *
 * <blockquote>
 * <strong>Implementation details:</strong>
 * <p>
 * To use this store, your {@link Config} interface must extend or implement {@link Accessible}. It should also
 * avoid the usage of the {@link org.aeonbits.owner.Config.Sources} or {@link org.aeonbits.owner.Config.HotReload}
 * annotations since io operations are handled by the file store.
 * </blockquote>
 *
 * @param <T> the stored config type
 */
public class ConfigFileStore<T extends Accessible> extends AbstractFileStore<T> {
    private final Factory factory;
    private final Class<T> clazz;

    /**
     * Base constructor of the {@code ConfigFileStore}.
     *
     * @param path    the path of the file
     * @param clazz   the class of the stored config
     * @param factory the config factory instance
     * @param imports imported properties for the initial value of the file store
     */
    public ConfigFileStore(String path, Class<T> clazz, Factory factory, Map<?, ?>... imports) {
        super(new File(path), () -> factory.create(clazz, imports));
        this.factory = factory;
        this.clazz = clazz;
    }

    /**
     * This constructor uses the global factory instance of {@link ConfigFactory} as the {@link #factory}.
     *
     * @see ConfigFileStore#ConfigFileStore(String, Class, Factory, Map[])
     */
    public ConfigFileStore(String path, Class<T> clazz, Map<?, ?>... imports) {
        this(path, clazz, SingletonConfigFactory.getInstance(), imports);
    }

    /**
     * Static constructor that calls {@link #load()} directly after the {@code ConfigFileStore} creation.
     *
     * @see ConfigFileStore#ConfigFileStore(String, Class, Factory, Map[])
     */
    public static <T extends Accessible> ConfigFileStore<T> load(String path, Class<T> clazz, Factory factory, Map<?, ?>... imports) {
        ConfigFileStore<T> store = new ConfigFileStore<>(path, clazz, factory, imports);
        store.load();
        return store;
    }

    /**
     * Static constructor that calls {@link #load()} directly after the {@code ConfigFileStore} creation.
     *
     * @see ConfigFileStore#ConfigFileStore(String, Class, Map[])
     */
    public static <T extends Accessible> ConfigFileStore<T> load(String path, Class<T> clazz, Map<?, ?>... imports) {
        ConfigFileStore<T> store = new ConfigFileStore<>(path, clazz, imports);
        store.load();
        return store;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        getFile().getAbsoluteFile().getParentFile().mkdirs();

        try (final Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            get().fill(properties);
            properties.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the config at " + getFile(), e);
        }
    }

    @Override
    public void load() {
        if (!getFile().exists()) {
            save();
        } else {
            try (final Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
                Properties properties = new Properties();
                properties.load(reader);
                set(factory.create(clazz, properties));
            } catch (IOException e) {
                throw new RuntimeException("Unable to load the config at " + getFile(), e);
            }
        }
    }

    /**
     * @return the config factory instance
     */
    public Factory getFactory() {
        return factory;
    }

    /**
     * @return the class of the stored config
     */
    public Class<T> getConfigClass() {
        return clazz;
    }
}
