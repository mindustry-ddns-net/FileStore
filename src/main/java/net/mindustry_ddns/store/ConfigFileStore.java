package net.mindustry_ddns.store;

import org.aeonbits.owner.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class ConfigFileStore<T extends Accessible> implements FileStore<T> {
    private final Class<T> clazz;
    private final Factory factory;
    private File file;
    private T config = null;

    public ConfigFileStore(File file, Class<T> clazz, Factory factory) {
        this.file = file;
        this.clazz = clazz;
        this.factory = factory;
    }

    public ConfigFileStore(File file, Class<T> clazz) {
        this(file, clazz, ConfigFactory.newInstance());
    }

    public Factory getFactory() {
        return factory;
    }

    @Override
    public T get() {
        return config;
    }

    @Override
    public Class<T> getObjectClass() {
        return clazz;
    }

    @Override
    public void set(T config) {
        throw new UnsupportedOperationException("Configs can't be set.");
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        file.getAbsoluteFile().getParentFile().mkdirs();

        try (OutputStream stream = new FileOutputStream(file)) {
            config.store(stream, null);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the config at " + file, e);
        }
    }

    @Override
    public void load() {
        Properties properties = new Properties();

        if (file.exists()) {
            try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                properties.load(reader);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load the config at " + file, e);
            }
        }

        this.config = factory.create(clazz, properties);
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }
}
