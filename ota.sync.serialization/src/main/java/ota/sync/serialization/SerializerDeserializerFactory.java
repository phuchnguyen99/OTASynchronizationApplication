package ota.sync.serialization;

import ota.sync.serialization.utils.SerializerDeserializer;

/**
 * SerializerDerserializerFactory generate type of serializer
 * de-serializer on call.
 */
public interface SerializerDeserializerFactory
{
    /**
     * get json serializer de-serializer.
     * @return json serializer de-serializer.
     */
    SerializerDeserializer getJsonSerializerDeserializer();
}
