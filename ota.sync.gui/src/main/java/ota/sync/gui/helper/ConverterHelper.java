package ota.sync.gui.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ConverterHelper class to assist with needed conversion.
 */
public class ConverterHelper
{
    /**
     * convert date to String.
     * @param date date.
     * @return string date.
     */
    public static String convertDateToString(final Date date)
    {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * convert status boolean to string.
     * @param status status.
     * @return succeed if the status is true || false if the status is false.
     */
    public static String convertStatusBooleanToString(final boolean status)
    {
        return status ? "Succeed" : "Fail";
    }
}
