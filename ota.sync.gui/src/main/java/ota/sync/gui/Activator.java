package ota.sync.gui;

import ota.sync.database.DatabaseService;
import ota.sync.database.DefaultDatabaseService;
import ota.sync.database.OtaDatabaseService;
import ota.sync.database.cache.DefaultUserCacheHelper;
import ota.sync.database.cache.DefaultVehicleCacheHelper;
import ota.sync.database.cache.UserCacheHelper;
import ota.sync.database.cache.VehicleCacheHelper;
import ota.sync.notifier.DefaultNotifier;
import ota.sync.notifier.Notifier;
import ota.sync.serialization.DefaultSerializerDeserializerFactory;
import ota.sync.user.UserService;
import ota.sync.user.dft.DefaultUserService;
import ota.sync.vehicle.VehicleService;
import ota.sync.vehicle.dft.DefaultVehicleService;

/**
 * The Activator class initiates all services class in order to reduce
 * service objects created throughout the application and manage the created
 * objects.
 */
public class Activator
{
    /** database service */
    private static final DatabaseService databaseService;
    /** ota database service */
    private static final DatabaseService otaDatabaseService;
    /** vehicle cache helper */
    private static final VehicleCacheHelper vehicleCacheHelper;
    /** user cache helper */
    private static final UserCacheHelper userCacheHelper;
    /** user service */
    private static final UserService userService;
    /** vehicle service */
    private static final VehicleService vehicleService;
    /** notifier */
    private static final Notifier notifier;

    /**
     * static block to initiate service classes when
     * the Activator class is loaded to jvm.
     */
    static
    {
        databaseService = new DefaultDatabaseService();
        otaDatabaseService = new OtaDatabaseService();
        vehicleCacheHelper = new DefaultVehicleCacheHelper(databaseService, otaDatabaseService);
        userCacheHelper = new DefaultUserCacheHelper(databaseService);
        userService = new DefaultUserService(userCacheHelper);
        vehicleService = new DefaultVehicleService(vehicleCacheHelper, new DefaultSerializerDeserializerFactory());
        notifier = new DefaultNotifier();
    }

    /**
     * get user service.
     * @return user service.
     */
    public static UserService getUserService()
    {
        return userService;
    }

    /**
     * get vehicle service.
     * @return vehicle service.
     */
    public static VehicleService getVehicleService()
    {
        return vehicleService;
    }

    /**
     * get notifier.
     * @return notifier.
     */
    public static Notifier getNotifier()
    {
        return notifier;
    }
}
