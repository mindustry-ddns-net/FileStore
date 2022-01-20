package net.mindustry_ddns.store;

import org.aeonbits.owner.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;


public class ConfigStore<T extends Config&Accessible> implements ObjectStore<T>{
    private final File directory;
    private final Factory factory;

    public ConfigStore(String directory, Factory factory){
        this.directory = new File(directory);
        this.factory = factory;
    }

    public ConfigStore(String directory){
        this(directory, ConfigFactory.newInstance());
    }

    @Override public void store(String name, T config){
        final var file = new File(directory, name + ".properties");
        file.getAbsoluteFile().getParentFile().mkdirs();

        try(final var out = new FileOutputStream(file)){
            config.store(out, name + " configuration file");
        }catch(IOException e){
            throw new RuntimeException("Failed to save the config " + config.getClass().getName() + " at " + file.getAbsolutePath(), e);
        }
    }

    @Override public <R extends T> R load(String name, Class<R> clazz){
        final var properties = new Properties();
        final var file = new File(directory, name + ".properties");

        if(file.exists()){
            try(final var in = new FileReader(file, StandardCharsets.UTF_8)){
                properties.load(in);
            }catch(IOException e){
                throw new RuntimeException("Failed to load the config " + clazz.getSimpleName() + " at " + file.getAbsolutePath(), e);
            }
        }

        return factory.create(clazz, properties);
    }
}
