package ota.sync.serialization;

import ota.sync.serialization.utils.JsonSerializerDeserializer;
import ota.sync.serialization.utils.SerializerDeserializer;

/**
 * Implementation of SerializerDeserializerFactory.
 */
public class DefaultSerializerDeserializerFactory implements SerializerDeserializerFactory
{
    @Override
    public SerializerDeserializer getJsonSerializerDeserializer()
    {
        return new JsonSerializerDeserializer();
    }
}
