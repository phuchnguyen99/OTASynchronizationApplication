package ota.sync.serialization.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * SerializerDeserializer service handles read files to objects
 * and write objects to files.
 */
public interface SerializerDeserializer
{
    /**
     * write value.
     * @param file file.
     * @param value value.
     * @throws IOException if operation fails.
     */
    void writeValue(File file,  Object value) throws IOException;

    /**
     * read value.
     * @param url url file.
     * @param clazz clazz.
     * @param <T> generic clazz.
     * @return read class as objects.
     * @throws IOException if operation fails.
     */
    <T> T readValue(URL url, Class<T> clazz) throws IOException;
}
