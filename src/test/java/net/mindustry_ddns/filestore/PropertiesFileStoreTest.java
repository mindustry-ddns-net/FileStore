package net.mindustry_ddns.filestore;

import net.mindustry_ddns.filestore.old.PropertiesFileStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesFileStoreTest {

    @TempDir
    @SuppressWarnings("unused")
    private Path temporaryDir;
    private String path;

    @BeforeEach
    void setup() {
        // Adding "test-dir" to check if it creates the parent directories
        path = temporaryDir.resolve("test-dir").resolve("test.properties").toAbsolutePath().toString();
    }

    @Test
    void test_file_save() throws IOException {
        PropertiesFileStore store = new PropertiesFileStore(path);

        // Checks if it saves correctly
        assertFalse(store.getFile().exists());
        store.save();
        assertTrue(store.getFile().exists());

        // Checks if it saves the new config correctly
        Properties properties = new Properties();
        properties.put("test.lucky-number", "13");
        store.set(properties);
        store.save();

        try (Reader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            Properties loaded = new Properties();
            loaded.load(reader);
            assertEquals(loaded.getProperty("test.lucky-number"), "13");
        }
    }

    @Test
    void test_file_load() throws IOException {
        PropertiesFileStore store = new PropertiesFileStore(path);

        // Checks if it creates the file when it does not exist
        assertFalse(store.getFile().exists());
        store.load();
        assertTrue(store.getFile().exists());

        // Checks if it loads the new value correctly
        Files.writeString(Path.of(path), "test.lucky-number = 13");
        store.load();
        assertEquals(store.get().getProperty("test.lucky-number"), "13");
    }

    @Test
    void test_properties_imports() {
        Map<String, String> import1 = Map.of("test.prop1", "12");
        Map<String, String> import2 = Map.of("test.prop2", "45");
        PropertiesFileStore store = new PropertiesFileStore(path, import1, import2);

        assertEquals(store.get().getProperty("test.prop1"), "12");
        assertEquals(store.get().getProperty("test.prop2"), "45");
    }
}
