package ota.sync.database;

import ota.sync.database.exception.DatabaseException;
import ota.sync.database.helper.DatabaseHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of DatabaseService
 */
public abstract class AbstractDatabaseService implements DatabaseService
{
    @Override
    public synchronized boolean executeSql(final String sqlQuery, final List<Object> arguments) throws DatabaseException
    {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery))
        {
            DatabaseHelper.fillPrepareStatement(arguments, preparedStatement);
            return preparedStatement.execute();
        }
        catch(final IOException | SQLException e)
        {
            throw new DatabaseException(e);
        }
    }

    public synchronized List<ResultRowMap> executeQuery(final String sqlQuery, final List<Object> arguments) throws DatabaseException
    {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery))
        {
            DatabaseHelper.fillPrepareStatement(arguments, preparedStatement);
            final ResultSet resultSet = preparedStatement.executeQuery();
            return DatabaseHelper.convertResultSetToResultRowMap(resultSet);
        }
        catch(final IOException | SQLException e)
        {
            throw new DatabaseException(e);
        }
    }

    protected synchronized Connection getConnection() throws DatabaseException, IOException
    {
        return null;
    }
}
