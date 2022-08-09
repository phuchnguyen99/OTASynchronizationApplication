package ota.sync.database;

import org.apache.commons.io.FileUtils;
import ota.sync.database.exception.DatabaseException;
import ota.sync.database.helper.DatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementation of AbstractDatabaseService.
 */
public class DefaultDatabaseService extends AbstractDatabaseService
{
    private String path;
    private Connection connection;

    @Override
    public synchronized void removeDatabase() throws SQLException
    {
        final Connection connection = this.connection;
        if(connection != null)
        {
            connection.close();
        }
        if(path != null)
        {
            FileUtils.deleteQuietly(new File(path));
        }
    }

    /**
     * get dealership connection.
     * @return connection.
     * @throws DatabaseException if operation fails.
     */
    public synchronized Connection getConnection() throws DatabaseException
    {
        try
        {
            if(path == null)
            {
                final File dbFile = new File("db/dealership.db");
                path = dbFile.getCanonicalPath();
                if(!dbFile.exists())
                {
                    FileUtils.forceMkdir(dbFile.getParentFile());
                    init();
                }
            }
            if(connection == null)
            {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
            }
        }
        catch(final IOException | ClassNotFoundException | SQLException e)
        {
            throw new DatabaseException(e);
        }
        return connection;
    }

    /**
     * init
     * @throws DatabaseException if the operation fails.
     */
    private void init() throws DatabaseException
    {
        new DatabaseSchemaBuilder().buildDealershipSchema(this);
    }
}
