package ota.sync.vehicle.dft;

import ota.sync.database.cache.VehicleCacheHelper;
import ota.sync.database.exception.DatabaseException;
import ota.sync.serialization.SerializerDeserializerFactory;
import ota.sync.serialization.utils.SerializerDeserializer;
import ota.sync.vehicle.VehicleData;
import ota.sync.vehicle.VehicleService;
import ota.sync.vehicle.exception.VehicleServiceException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Implementation of VehicleService.
 */
public class DefaultVehicleService implements VehicleService
{
    private VehicleCacheHelper vehicleCacheHelper;
    private final SerializerDeserializer jsonSerializerDeserializer;

    /**
     * Constructor.
     * @param vehicleCacheHelper vehicle cache helper.
     * @param serializerDeserializerFactory serializer de-serializer factory.
     */
    public DefaultVehicleService(final VehicleCacheHelper vehicleCacheHelper, final SerializerDeserializerFactory serializerDeserializerFactory)
    {
        this.vehicleCacheHelper = vehicleCacheHelper;
        jsonSerializerDeserializer = serializerDeserializerFactory.getJsonSerializerDeserializer();
    }

    @Override
    public List<VehicleData> getVehicleData(final String vin) throws VehicleServiceException
    {
        validateVin(vin);
        try
        {
            return vehicleCacheHelper.getVehicle(new VehicleData(vin));
        }
        catch(final DatabaseException e)
        {
            throw new VehicleServiceException(String.format("Unable to retrieve vehicle data for %s.", vin), e);
        }
    }

    @Override
    public List<VehicleData> refreshVehicleData(final String vin) throws VehicleServiceException
    {
        try
        {
            return vehicleCacheHelper.getSyncVehicleData(new VehicleData(vin));
        }
        catch(final DatabaseException e)
        {
            throw new VehicleServiceException("Unable to refresh vehicle data", e);
        }
    }

    @Override
    public boolean addVehicleData(final VehicleData vehicleData) throws VehicleServiceException
    {
        try
        {
            return  vehicleCacheHelper.createNewVehicleData(vehicleData);
        }
        catch(final DatabaseException e)
        {
            throw new VehicleServiceException(e);
        }
    }

    @Override
    public boolean modifyVehicleData(final VehicleData vehicleData) throws VehicleServiceException
    {
        try
        {
            return vehicleCacheHelper.modifyVehicle(vehicleData);
        }
        catch(final DatabaseException e)
        {
            throw new VehicleServiceException(String.format("Unable to update vehicle data for %s.", vehicleData.getVin()), e);
        }
    }

    @Override
    public File exportVehicleData(final List<VehicleData> vehicleDataList) throws VehicleServiceException
    {
        try
        {
            final Date creationDate = new Date();
            final File file = new File(vehicleDataList.get(0).getVin()+ "_" + creationDate.getTime());
            jsonSerializerDeserializer.writeValue(file, vehicleDataList);
            return file;
        }
        catch(final IOException e)
        {
            throw new VehicleServiceException("Unable to export file", e);
        }
    }

    @Override
    public boolean deleteVehicleData(final VehicleData vehicleData)
        throws VehicleServiceException
    {
        try
        {
            return vehicleCacheHelper.deleteVehicle(vehicleData);
        }
        catch(final DatabaseException ex)
        {
            throw new VehicleServiceException("Unable to delete vehicle", ex);
        }
    }

    /**
     * Validate vin.
     * 1. vin must be 11 length.
     * 2. vin must start with LAF.
     *
     * @param vin vin
     * @throws VehicleServiceException if operations fails.
     */
    private void validateVin(final String vin) throws VehicleServiceException
    {
        if(vin.length() != 15)
        {
            throw new VehicleServiceException("Vin length must be 15 characters.");
        }
        if(!vin.startsWith("LAF"))
        {
            throw new VehicleServiceException("Vin is invalid.");
        }
    }
}
