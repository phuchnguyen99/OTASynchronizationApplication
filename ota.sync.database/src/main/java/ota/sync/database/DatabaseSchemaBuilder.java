package ota.sync.database;

import org.apache.commons.io.IOUtils;
import ota.sync.database.exception.DatabaseException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The class to build database off schema.
 */
public class DatabaseSchemaBuilder
{
    /** dealership schema path */
    private static final String DEALERSHIP_SCHEMA_PATH = "/db/schema.sql";
    /** ota schema path */
    private static final String OTA_SCHEMA_PATH = "/db/ota_schema.sql";

    /**
     * build ota schema.
     * @param databaseService database service.
     * @throws DatabaseException if operation fails.
     */
    public void buildOtaSchema(final DatabaseService databaseService) throws DatabaseException
    {
        buildSchema(databaseService, OTA_SCHEMA_PATH);
    }

    /**
     * build dealership schema.
     * @param databaseService database service.
     * @throws DatabaseException if the operation fails.
     */
    public void buildDealershipSchema(final DatabaseService databaseService) throws DatabaseException
    {
        buildSchema(databaseService, DEALERSHIP_SCHEMA_PATH);
    }

    /**
     * build schema.
     * @param databaseService database service.
     * @param schemaPath schema path.
     * @throws DatabaseException if operation fails.
     */
    private void buildSchema(final DatabaseService databaseService, final String schemaPath) throws DatabaseException
    {
        try
        {
            final URL url = DatabaseSchemaBuilder.class.getResource(schemaPath);
            executeSql(url.openStream(), databaseService);
        }
        catch(final IOException e)
        {
            throw new DatabaseException("Schema is unavailable.", e);
        }
    }

    /**
     * execute sql.
     * @param inputStream input stream.
     * @param databaseService database service.
     * @throws DatabaseException if the operation fails.
     */
    private void executeSql(final InputStream inputStream, final DatabaseService databaseService)
                            throws DatabaseException
    {
        try
        {
            final String[] queries = getQueries(inputStream);
            for(final String query : queries)
            {
                databaseService.executeSql(query, null);
            }
        }
        catch(final IOException e)
        {
            throw new DatabaseException("Queries are invalid.", e);
        }
    }

    /**
     * get queries.
     * @param uri uri.
     * @return string array of query.
     * @throws IOException if the operation fails.
     */
    private String[] getQueries(final InputStream uri) throws IOException
    {
        if(uri != null)
        {
            final String fileContent = new String(IOUtils.toByteArray(uri), StandardCharsets.UTF_8);
            return fileContent.split(";");
        }
        return null;
    }
}
