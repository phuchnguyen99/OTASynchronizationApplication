package ota.sync.serialization.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of SerializerDeserializer
 */
public class JsonSerializerDeserializer implements SerializerDeserializer
{
    /** object mapper */
    private ObjectMapper objectMapper;

    @Override
    public void writeValue(final File file, final Object value) throws IOException
    {
        getObjectMapper().writeValue(file, value);
    }

    @Override
    public <T> T readValue(final URL url, final Class<T> clazz) throws IOException
    {
        return getObjectMapper().readValue(url, clazz);
    }

    private ObjectMapper getObjectMapper()
    {
        if(objectMapper == null)
        {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
