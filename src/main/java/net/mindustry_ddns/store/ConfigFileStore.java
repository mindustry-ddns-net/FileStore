package net.mindustry_ddns.store;

import net.mindustry_ddns.store.util.*;
import org.aeonbits.owner.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;


/**
 * A property based store backed by the OWNER library.
 * Make sure to read the <a href="http://owner.aeonbits.org/docs/usage/">base documentation</a> before using this store.
 * It is recommended to avoid the usage of {@link org.aeonbits.owner.Config.Sources} or {@link org.aeonbits.owner.Config.HotReload} annotations
 * on your {@link Config} due to the fact it is already handled by this store.
 *
 * @param <T> the config type
 */
public class ConfigFileStore<T extends Accessible> extends AbstractFileStore<T> {
    private final Factory factory;
    private final Class<T> clazz;

    public ConfigFileStore(File file, Class<T> clazz, Factory factory) {
        super(file, () -> factory.create(clazz));
        this.factory = factory;
        this.clazz = clazz;
        load();
    }

    public ConfigFileStore(File file, Class<T> clazz) {
        this(file, clazz, SingletonConfigFactory.getInstance());
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

        set(factory.create(clazz, properties));
    }
}
