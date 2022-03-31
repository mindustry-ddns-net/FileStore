package net.mindustry_ddns.filestore.old;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Converter;
import org.aeonbits.owner.Factory;
import org.aeonbits.owner.loaders.Loader;

import java.util.Map;
import java.util.Properties;


/**
 * A {@link Factory} view of {@link ConfigFactory}.
 */
public final class SingletonConfigFactory implements Factory {

    private static final SingletonConfigFactory INSTANCE = new SingletonConfigFactory();

    public static SingletonConfigFactory getInstance() {
        return INSTANCE;
    }

    private SingletonConfigFactory() {

    }

    @Override
    public <T extends Config> T create(Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigFactory.create(clazz, imports);
    }

    @Override
    public String getProperty(String key) {
        return ConfigFactory.getProperty(key);
    }

    @Override
    public String setProperty(String key, String value) {
        return ConfigFactory.setProperty(key, value);
    }

    @Override
    public String clearProperty(String key) {
        return ConfigFactory.clearProperty(key);
    }

    @Override
    public Properties getProperties() {
        return ConfigFactory.getProperties();
    }

    @Override
    public void setProperties(Properties properties) {
        ConfigFactory.setProperties(properties);
    }

    @Override
    public void registerLoader(Loader loader) {
        ConfigFactory.registerLoader(loader);
    }

    @Override
    public void setTypeConverter(Class<?> type, Class<? extends Converter<?>> converter) {
        ConfigFactory.setTypeConverter(type, converter);
    }

    @Override
    public void removeTypeConverter(Class<?> type) {
        ConfigFactory.removeTypeConverter(type);
    }
}
