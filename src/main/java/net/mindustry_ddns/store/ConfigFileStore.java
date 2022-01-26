package net.mindustry_ddns.store;

import net.mindustry_ddns.store.util.*;
import org.aeonbits.owner.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.function.*;


public class ConfigFileStore<T extends Accessible> extends AbstractFileStore<T> {
    private final Factory factory;

    public ConfigFileStore(File file, Class<T> clazz, Supplier<T> supplier, Factory factory) {
        super(file, clazz, supplier);
        this.factory = factory;
    }

    public ConfigFileStore(File file, Class<T> clazz, Supplier<T> supplier) {
        this(file, clazz, supplier, SingletonConfigFactory.getInstance());
    }

    public ConfigFileStore(File file, Class<T> clazz) {
        this(file, clazz, () -> SingletonConfigFactory.getInstance().create(clazz));
    }

    public static <T extends Accessible> ConfigFileStore<T> of(File file, Class<T> clazz, Supplier<T> supplier, Factory factory) {
        ConfigFileStore<T> store = new ConfigFileStore<>(file, clazz, supplier, factory);
        store.reload();
        return store;
    }

    public static <T extends Accessible> ConfigFileStore<T> of(File file, Class<T> clazz, Supplier<T> supplier) {
        ConfigFileStore<T> store = new ConfigFileStore<>(file, clazz, supplier);
        store.reload();
        return store;
    }

    public Factory getFactory() {
        return factory;
    }

    @Override
    public void save() {
        try (final OutputStream out = new FileOutputStream(getFile())) {
            get().store(out, null);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the config at " + getFile(), e);
        }
    }

    @Override
    protected T load() {
        if (!getFile().exists()) return get();

        Properties properties = new Properties();

        if (getFile().exists()) {
            try (final var reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
                properties.load(reader);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load the config at " + getFile(), e);
            }
        }

        return factory.create(getObjectClass(), properties);
    }
}
