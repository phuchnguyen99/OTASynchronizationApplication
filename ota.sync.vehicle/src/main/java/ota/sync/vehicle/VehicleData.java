package ota.sync.vehicle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

public class VehicleData
{
    /** vin */
    private String vin;
    /** module name */
    private String moduleName;
    /** software number */
    private String softwareNumber;
    /** status */
    private boolean status;
    /** timestamp */
    private Date timestamp;
    /** creation time */
    private Date creationTime;

    /**
     * Constructor.
     * @param vin vin.
     * @param moduleName module name.
     * @param softwareNumber software number.
     * @param status status.
     * @param timestamp timestamp.
     * @param creationTime creation time.
     */
    @JsonCreator
    public VehicleData(@JsonProperty("vin") final String vin, @JsonProperty("moduleName") final String moduleName,
                       @JsonProperty("softwareNumber") final String softwareNumber, @JsonProperty("status") final boolean status,
                       @JsonProperty("timestamp") final Date timestamp, @JsonProperty("creationTime") final Date creationTime)
    {
        this.vin = vin;
        this.moduleName = moduleName;
        this.softwareNumber = softwareNumber;
        this.status = status;
        this.timestamp = timestamp;
        this.creationTime = creationTime;
    }

    /**
     * Constructor.
     * @param vin vin.
     */
    public VehicleData(final String vin)
    {
        this.vin = vin;
    }

    /**
     * get vin.
     * @return
     */
    public String getVin()
    {
        return vin;
    }

    /**
     * get module name.
     * @return module name.
     */
    public String getModuleName()
    {
        return moduleName;
    }

    /**
     * get software number.
     * @return software number.
     */
    public String getSoftwareNumber()
    {
        return softwareNumber;
    }

    /**
     * get status.
     * @return status.
     */
    public boolean getStatus()
    {
        return status;
    }

    /**
     * get creation time.
     * @return creation time.
     */
    public Date getCreationTime()
    {
        return creationTime;
    }

    /**
     * get timestamp.
     * @return timestamp.
     */
    public Date getTimestamp()
    {
        return timestamp;
    }

    @Override
    public boolean equals(final Object o)
    {
        if(o == null || o.getClass() != this.getClass())
        {
            return false;
        }
        if(this == o)
        {
            return true;
        }

        final VehicleData that = (VehicleData)o;
        return new EqualsBuilder().append(this.vin, that.vin)
                                  .append(this.moduleName, that.moduleName)
                                  .append(this.softwareNumber, that.softwareNumber)
                                  .append(this.status, that.status)
                                  .append(this.timestamp, that.timestamp)
                                  .append(this.creationTime, that.creationTime).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(vin).append(moduleName).append(softwareNumber)
                                    .append(status).append(timestamp).append(creationTime).hashCode();
    }
}
