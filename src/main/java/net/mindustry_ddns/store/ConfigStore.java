package net.mindustry_ddns.store;

import org.aeonbits.owner.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class ConfigStore<T extends Config&Accessible> implements ObjectStore<T> {

    private final File directory;
    private final Factory factory;

    public ConfigStore(String directory, Factory factory){
        this.directory = new File(directory);
        this.factory = factory;
    }

    public ConfigStore(String directory) {
        this(directory, ConfigFactory.newInstance());
    }

    @Override
    public void store(String name, T config) {

        File file = new File(directory, name + ".properties");

        if (!file.getAbsoluteFile().getParentFile().mkdirs())
            throw new RuntimeException("Failed to create the config folder at " + file.getAbsolutePath());

        try {
            config.store(new FileOutputStream(file), name + " configuration file");
        } catch(IOException e) {
            throw new RuntimeException("Failed to save the config " + config.getClass().getName() + " at " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public <R extends T> R load(String name, Class<R> clazz) {

        Properties properties = new Properties();
        File file = new File(directory, name + ".properties");

        if (file.exists()) {

            try {
                properties.load(new FileReader(file, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load the config " + clazz.getSimpleName() + " at " + file.getAbsolutePath(), e);
            }
        }

        return factory.create(clazz, properties);
    }

    public File getDirectory() {
        return directory;
    }

    public Factory getFactory() {
        return factory;
    }
}
