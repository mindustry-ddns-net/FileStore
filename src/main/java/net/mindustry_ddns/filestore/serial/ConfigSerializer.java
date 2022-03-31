package net.mindustry_ddns.filestore.serial;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Factory;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Properties;

final class ConfigSerializer<T extends Accessible> implements Serializer<T> {

    private final Factory factory;
    private final Class<T> type;

    public ConfigSerializer(Class<T> type, Factory factory) {
        this.type = type;
        this.factory = factory;
    }

    public ConfigSerializer(Class<T> type) {
        this(type, null);
    }

    // Type is null
    public T deserialize(Reader reader, Type type) throws IOException {

        Properties properties = new Properties();
        properties.load(reader);
        return factory == null ? ConfigFactory.create(this.type, properties) : factory.create(this.type, properties);
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        Properties properties = new Properties();
        object.fill(properties);
        properties.store(writer, null);
    }
}
