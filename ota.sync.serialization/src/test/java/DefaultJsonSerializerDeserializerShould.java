import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import ota.sync.serialization.utils.JsonSerializerDeserializer;
import ota.sync.serialization.utils.SerializerDeserializer;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class DefaultJsonSerializerDeserializerShould
{
    private final SerializerDeserializer serializerDeserializer = new JsonSerializerDeserializer();

    @Test
    public void write_object_to_files_and_read_file_to_object() throws Exception
    {
        final File file  = new File("test.json");
        serializerDeserializer.writeValue(file, new TestObject("value"));

        final TestObject readObject = serializerDeserializer.readValue(file.toURL(), TestObject.class);
        assertThat(readObject, notNullValue());
    }

    private static class TestObject
    {
        @JsonProperty
        public String value;

        /**
         * private constructor for de-serialization.
         */
        private TestObject()
        {

        }
        @JsonCreator
        public TestObject(@JsonProperty final String value)
        {
            this.value = value;
        }
    }
}
