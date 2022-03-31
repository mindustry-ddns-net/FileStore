package net.mindustry_ddns.filestore.serial;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Properties;

final class PropertiesSerializer implements StoreSerializer<Properties> {

    static final PropertiesSerializer INSTANCE = new PropertiesSerializer();

    @Override
    public Properties read(Reader reader, Type type) throws IOException {
        final var properties = new Properties();
        properties.load(reader);
        return properties;
    }

    @Override
    public void write(Writer writer, Properties object) throws IOException {
        object.store(writer, null);
    }
}
