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
        load();
    }

    public ConfigFileStore(File file, Class<T> clazz, Supplier<T> supplier) {
        this(file, clazz, supplier, SingletonConfigFactory.getInstance());
    }

    public ConfigFileStore(File file, Class<T> clazz) {
        this(file, clazz, () -> SingletonConfigFactory.getInstance().create(clazz));
    }

    public Factory getFactory() {
        return factory;
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
            return;
        }

        Properties properties = new Properties();

        try (final Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the config at " + getFile(), e);
        }

        set(factory.create(getObjectClass(), properties));
    }
}
