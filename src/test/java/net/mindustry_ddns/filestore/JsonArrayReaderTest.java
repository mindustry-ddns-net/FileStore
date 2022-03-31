package net.mindustry_ddns.filestore;

import net.mindustry_ddns.filestore.old.JsonArrayReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonArrayReaderTest {

    private static final int[] EMPTY_ARRAY = {};
    private static final int[] TEST_ARRAY = {1, 2, 3, 4};

    @Test
    void test_normal_array() throws IOException {
        test_json_array_reader("int-array.json", TEST_ARRAY);
    }

    @Test
    void test_empty_array() throws IOException {
        test_json_array_reader("empty-array.json", EMPTY_ARRAY);
    }

    @SuppressWarnings("ConstantConditions")
    void test_json_array_reader(String fileName, int[] expectedArray) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
             Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            Iterator<Integer> iterator = new JsonArrayReader<>(reader, Integer.TYPE);
            int[] array = new int[expectedArray.length];
            for (int i = 0; i < array.length; i++) {
                assertTrue(iterator.hasNext());
                array[i] = iterator.next();
            }

            assertArrayEquals(expectedArray, array);
            assertFalse(iterator.hasNext());
            assertThrows(NoSuchElementException.class, iterator::next);
        }
    }
}
