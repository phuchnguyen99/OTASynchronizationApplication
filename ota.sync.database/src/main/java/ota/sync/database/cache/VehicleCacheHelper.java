package ota.sync.database.cache;

import ota.sync.database.exception.DatabaseException;
import ota.sync.vehicle.VehicleData;

import java.util.List;

/**
 * VehicleCacheHelper
 */
public interface VehicleCacheHelper
{
    /**
     * get vehicle.
     * @param vehicleData vehicle data.
     * @return list of vehicle data.
     * @throws DatabaseException if operation fails.
     */
    List<VehicleData> getVehicle(VehicleData vehicleData) throws DatabaseException;

    /**
     * modify vehicle.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws DatabaseException if operation fails.
     */
    boolean modifyVehicle(VehicleData vehicleData) throws DatabaseException;

    /**
     * create new vehicle data.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws DatabaseException if operation fails.
     */
    boolean createNewVehicleData(VehicleData vehicleData) throws DatabaseException;

    /**
     * delete vehicle.
     * @param vehicleData vehicle data.
     * @return true if succeeds.
     * @throws DatabaseException if operation fails.
     */
    boolean deleteVehicle(VehicleData vehicleData) throws DatabaseException;

    /**
     * get sync vehicle data.
     * @param vehicleData vehicle data.
     * @return vehicle data list.
     * @throws DatabaseException if operation fails.
     */
    List<VehicleData> getSyncVehicleData(VehicleData vehicleData) throws DatabaseException;
}
