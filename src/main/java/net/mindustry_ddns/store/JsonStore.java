package net.mindustry_ddns.store;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.*;


public class JsonStore implements ObjectStore<Object>{
    private final File directory;
    private final Gson gson;

    public JsonStore(String directory, Gson gson){
        this.directory = new File(directory);
        this.gson = gson;
    }

    public JsonStore(String directory){
        this(directory, new Gson());
    }

    @Override public void store(String name, Object object){
        final var file = new File(directory, name + ".json");
        file.getAbsoluteFile().getParentFile().mkdirs();

        try(final var writer = new FileWriter(file, StandardCharsets.UTF_8)){
            gson.toJson(object, object.getClass(), writer);
        }catch(IOException e){
            throw new RuntimeException("Failed to store the " + object.getClass().getName() + " at " + file.getAbsolutePath() + " as json.", e);
        }
    }

    @Override public <T> T load(String name, Class<T> clazz){
        final var file = new File(directory, name + ".json");

        try(final var reader = new FileReader(file, StandardCharsets.UTF_8)){
            return gson.fromJson(reader, clazz);
        }catch(IOException e){
            throw new RuntimeException("Unable to store the object at the given location");
        }
    }
}
