package ota.sync.database;

import ota.sync.database.exception.DatabaseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * ResultRowMap.
 */
public class ResultRowMap extends HashMap<String, Object>
{
    /**
     * get String.
     * @param columnName column name.
     * @return string.
     */
    public String getString(final String columnName)
    {
        return (String) this.get(columnName);
    }

    /**
     * get date.
     * @param columnName column name.
     * @return date.
     * @throws DatabaseException if the operation fails.
     */
    public Date getDate(final String columnName) throws DatabaseException
    {
        final Object value = this.get(columnName);
        return new Date((Long)value);
    }

    /**
     * get boolean.
     * @param columnName column name.
     * @return boolean.
     */
    public Boolean getBoolean(final String columnName)
    {
        final Integer value = (Integer) this.get(columnName);
        return  value != null && value == 1;
    }
}
