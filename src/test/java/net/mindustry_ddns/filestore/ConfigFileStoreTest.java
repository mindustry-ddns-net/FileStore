package net.mindustry_ddns.filestore;

import net.mindustry_ddns.filestore.util.*;
import org.aeonbits.owner.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigFileStoreTest {
    @TempDir
    @SuppressWarnings("unused")
    private Path temporaryDir;
    private String path;

    @BeforeEach
    public void setup() {
        // Adding "test-dir" to check if it creates the parent directories
        path = temporaryDir.resolve("test-dir").resolve("test.properties").toAbsolutePath().toString();
    }

    @Test
    public void test_file_save() throws IOException {
        ConfigFileStore<TestConfig> store = new ConfigFileStore<>(path, TestConfig.class);

        // Checks if it saves correctly
        assertFalse(store.getFile().exists());
        store.save();
        assertTrue(store.getFile().exists());

        // Checks if it saves the new config correctly
        TestConfig config = ConfigFactory.create(TestConfig.class, Map.of("test.lucky-number", "13"));
        store.set(config);
        store.save();

        try (Reader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(reader);
            assertEquals(properties.getProperty("test.lucky-number"), "13");
        }
    }

    @Test
    public void test_file_load() throws IOException {
        ConfigFileStore<TestConfig> store = new ConfigFileStore<>(path, TestConfig.class);

        // Checks if it creates the file when it does not exist
        assertFalse(store.getFile().exists());
        store.load();
        assertTrue(store.getFile().exists());

        // Checks if it loads the new value correctly
        Files.writeString(Path.of(path), "test.lucky-number = 13");
        store.load();
        assertEquals(store.get().getLuckyNumber(), 13);
    }
}
