package net.mindustry_ddns.store;

import net.mindustry_ddns.store.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFileStoreTest {
    @TempDir
    @SuppressWarnings("unused")
    private Path temporaryDir;
    private String path;

    @BeforeEach
    public void setup() {
        // Adding "test-dir" to check if it creates the parent directories
        path = temporaryDir.resolve("test-dir").resolve("test.json").toAbsolutePath().toString();
    }

    @Test
    public void test_file_save() throws IOException {
        JsonFileStore<TestObject> store = new JsonFileStore<>(path, TestObject.class, TestObject::new);

        // Checks if it saves correctly
        assertFalse(store.getFile().exists());
        store.save();
        assertTrue(store.getFile().exists());

        // Checks if it saves the object correctly
        store.get().setLuckyNumber(13);
        store.save();
        try (Reader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            TestObject object = store.getGson().fromJson(reader, TestObject.class);
            assertEquals(object.getLuckyNumber(), 13);
        }
    }

    @Test
    public void test_file_load() throws IOException {
        JsonFileStore<TestObject> store = new JsonFileStore<>(path, TestObject.class, TestObject::new);

        // Checks if it creates the file when it does not exist
        assertFalse(store.getFile().exists());
        store.load();
        assertTrue(store.getFile().exists());

        // Checks if it loads the new value correctly
        Files.writeString(Path.of(path), "{\"luckyNumber\": 13}");
        store.load();
        assertEquals(store.get().getLuckyNumber(), 13);
    }
}
