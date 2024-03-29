package net.mindustry_ddns.filestore.serial;

import io.leangen.geantyref.TypeToken;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Factory;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

final class ConfigSerializer<T extends Accessible> implements Serializer<T> {

    private final Factory factory;

    ConfigSerializer(Factory factory) {
        this.factory = factory;
    }

    ConfigSerializer() {
        this.factory = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(Reader reader, TypeToken<T> token) throws IOException {
        if (token.getType() instanceof Class<?> clazz) {
            Properties properties = new Properties();
            properties.load(reader);
            return factory == null
                    ? ConfigFactory.create((Class<T>) clazz, properties)
                    : factory.create((Class<T>) clazz, properties);
        } else {
            throw new IOException("The type is invalid, it should be an interface with no generic parameters, not '"
                    + token.getType().getClass().toString()
                    + "'.");
        }
    }

    @Override
    public void serialize(Writer writer, T object) throws IOException {
        Properties properties = new Properties();
        object.fill(properties);
        properties.store(writer, null);
    }
}
