package net.mindustry_ddns.filestore.serial;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Factory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Properties;

final class ConfigSerializer<T extends Accessible> implements StoreSerializer<T> {

    private final Factory factory;

    public ConfigSerializer(Factory factory) {
        this.factory = factory;
    }

    public ConfigSerializer() {
        this(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(Reader reader, Type type) throws IOException {
        try {
            Properties properties = new Properties();
            properties.load(reader);
            final var clazz = (Class<T>) type;
            return factory == null ? ConfigFactory.create(clazz, properties) : factory.create(clazz, properties);
        } catch (ClassCastException e) {
            throw new IOException("The type is invalid, it should be a class literal.");
        }
    }

    @Override
    public void write(Writer writer, T object) throws IOException {
        Properties properties = new Properties();
        object.fill(properties);
        properties.store(writer, null);
    }
}
