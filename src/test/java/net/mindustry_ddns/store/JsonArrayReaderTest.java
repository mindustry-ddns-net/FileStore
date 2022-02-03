package net.mindustry_ddns.store;

import net.mindustry_ddns.store.util.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonArrayReaderTest {
    private static final int[] EMPTY_ARRAY = {};
    private static final int[] TEST_ARRAY = {1, 2, 3, 4};

    @Test
    public void test_normal_array() throws IOException {
        test_json_array_reader("int-array.json", TEST_ARRAY);
    }

    @Test
    public void test_empty_array() throws IOException {
        test_json_array_reader("empty-array.json", EMPTY_ARRAY);
    }

    @SuppressWarnings("ConstantConditions")
    public void test_json_array_reader(String fileName, int[] expectedArray) throws IOException {
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
