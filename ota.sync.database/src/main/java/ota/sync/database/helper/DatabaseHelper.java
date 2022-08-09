package ota.sync.database.helper;

import ota.sync.database.ResultRowMap;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * DatabaseHelper class to assist database services
 */
public class DatabaseHelper
{
    /**
     * private constructors to avoid
     * initiation.
     */
    private DatabaseHelper()
    {

    }

    /**
     * fill prepare statement.
     * @param arguments arguments.
     * @param statement statement.
     * @throws SQLException if operation fails.
     */
    public static void fillPrepareStatement(final List<Object> arguments, final PreparedStatement statement) throws SQLException
    {
        if(arguments == null || arguments.isEmpty())
        {
            return;
        }
        final ListIterator<Object> listIterator = arguments.listIterator();
        while(listIterator.hasNext())
        {
            int index = listIterator.nextIndex() + 1;
            final Object arg = listIterator.next();

            if(arg instanceof String)
            {
                statement.setString(index, (String)arg);
            }
            else if(arg instanceof Integer)
            {
                statement.setInt(index, (Integer) arg);
            }
            else if(arg instanceof java.util.Date)
            {
                statement.setDate(index, new Date(((java.util.Date)arg).getTime()));
            }
            else if(arg instanceof Boolean)
            {
                statement.setBoolean(index, (Boolean) arg);
            }
        }
    }

    /**
     * convert result set to result row map.
     * @param resultSet result set.
     * @return result row map list.
     * @throws SQLException if operation fails.
     */
    public static List<ResultRowMap> convertResultSetToResultRowMap(final ResultSet resultSet) throws SQLException
    {
        try
        {
            final List<ResultRowMap> resultRowMapList = new ArrayList<>();
            final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while(resultSet.next())
            {
                final ResultRowMap resultRowMap = new ResultRowMap();
                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                {
                    resultRowMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                resultRowMapList.add(resultRowMap);
            }
            return resultRowMapList;
        }
        finally
        {
            resultSet.close();
        }
    }
}
