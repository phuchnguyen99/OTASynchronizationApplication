package ota.sync.database;

import ota.sync.database.exception.DatabaseException;

import java.sql.SQLException;
import java.util.List;

/**
 * Database Service
 */
public interface DatabaseService
{
    /**
     * execute sql.
     * @param sqlQuery sql query.
     * @param arguments arguments.
     * @return true if succeeds.
     * @throws DatabaseException if the operation fails.
     */
    boolean executeSql(String sqlQuery, List<Object> arguments) throws DatabaseException;

    /**
     * remove database.
     * @throws SQLException if the operation fails.
     */
    void removeDatabase() throws SQLException;

    /**
     * execute query.
     * @param sqlQuery sql query.
     * @param arguments arguments.
     * @return result row map list.
     * @throws DatabaseException if the operation fails.
     */
    List<ResultRowMap> executeQuery(String sqlQuery, List<Object> arguments) throws DatabaseException;
}
