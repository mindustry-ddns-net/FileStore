package net.mindustry_ddns.store;

import com.google.gson.*;
import java.io.*;
import java.nio.charset.*;

public class JsonStore implements ObjectStore<Object> {

    private final File directory;
    private final Gson gson;

    public JsonStore(String directory, Gson gson) {
        this.directory = new File(directory);
        this.gson = gson;
    }

    public JsonStore(String directory){
        this(directory, new Gson());
    }

    @Override
    public void store(String name, Object object) {

        File file = new File(directory, name + ".json");

        if (!file.getAbsoluteFile().getParentFile().mkdirs())
            throw new RuntimeException("Failed to create the config folder at " + file.getAbsolutePath());

        try {
            gson.toJson(object, object.getClass(), new FileWriter(file, StandardCharsets.UTF_8));
        } catch(IOException e) {
            throw new RuntimeException("Failed to store the " + object.getClass().getName() + " at " + file.getAbsolutePath() + " as json.", e);
        }
    }

    @Override
    public <T> T load(String name, Class<T> clazz) {

        File file = new File(directory, name + ".json");

        try {
            return gson.fromJson(new FileReader(file, StandardCharsets.UTF_8), clazz);
        } catch(IOException e) {
            throw new RuntimeException("Unable to store the object at the given location");
        }
    }

    public File getDirectory() {
        return directory;
    }

    public Gson getGson() {
        return gson;
    }
}
