package net.mindustry_ddns.filestore.serial;

import io.leangen.geantyref.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

final class PropertiesSerializer implements Serializer<Properties> {

    static final PropertiesSerializer INSTANCE = new PropertiesSerializer();

    private PropertiesSerializer() {
    }

    @Override
    public Properties deserialize(Reader reader, TypeToken<Properties> token) throws IOException {
        final var properties = new Properties();
        properties.load(reader);
        return properties;
    }

    @Override
    public void serialize(Writer writer, Properties object) throws IOException {
        object.store(writer, null);
    }
}
