package com.bestapps.carwallet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Space;

import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.model.ServiceEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carwalletdb";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";

    private static final String CAR_TABLE = "car";
    private static final String CAR_MANUFACTURER = "manufacturer";
    private static final String CAR_MODEL = "model";
    private static final String CAR_SHAPE = "shape";
    private static final String CAR_ENGINE = "engine";
    private static final String CAR_POWER= "power";
    private static final String CAR_LICENSE_NO= "license_no";
    private static final String CAR_YEAR= "year";
    private static final String CAR_VIN= "vin";
    private static final String CAR_ACTIVE = "active";
    private static final String CAR_IMAGE = "image";
    private static final String CAR_MILEAGE = "mileage";
    private static final String CAR_TIMESTAMP = "timestamp";
    private static final String CAR_FUEL = "fuel_type";

    private static final String SERVICE_ENTRY_TABLE = "service_entry";
    private static final String SERVICE_ENTRY_TITLE = "title";
    private static final String SERVICE_ENTRY_DESCRIPTION = "description";
    private static final String SERVICE_ENTRY_MILEAGE = "mileage";
    private static final String SERVICE_ENTRY_PRICE = "price";
    private static final String SERVICE_ENTRY_DATE = "date";
    private static final String SERVICE_ENTRY_CAR_ID = "car_id";

    private static final String MAINTENANCE_TABLE = "maintenance";
    private static final String MAINTENANCE_TITLE= "title";
    private static final String MAINTENANCE_DESCRIPTION = "description";
    private static final String MAINTENANCE_MILEAGE = "mileage";
    private static final String MAINTENANCE_PRICE = "price";
    private static final String MAINTENANCE_DATE = "date";
    private static final String MAINTENANCE_NOTIFICATIONS = "notification_active";
    private static final String MAINTENANCE_CAR_ID = "car_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CAR_TABLE = "create table " + CAR_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + CAR_MANUFACTURER + " text, "
                + CAR_MODEL + " text, "
                + CAR_SHAPE + " text, "
                + CAR_ENGINE + " text, "
                + CAR_POWER + " integer, "
                + CAR_YEAR + " integer, "
                + CAR_VIN + " text, "
                + CAR_MILEAGE + " integer, "
                + CAR_FUEL + " text, "
                + CAR_LICENSE_NO+ " text, "
                + CAR_ACTIVE+ " integer, "
                + CAR_TIMESTAMP+ " integer, "
                + CAR_IMAGE+ " integer " +
                " ) ";
        String CREATE_SERVICE_ENTRY = "create table " + SERVICE_ENTRY_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + SERVICE_ENTRY_TITLE + " text, "
                + SERVICE_ENTRY_DESCRIPTION + " text, "
                + SERVICE_ENTRY_MILEAGE + " integer, "
                + SERVICE_ENTRY_PRICE + " integer, "
                + SERVICE_ENTRY_DATE + " text, "
                + SERVICE_ENTRY_CAR_ID + " integer " +
                " ) ";

        String CREATE_MAINTENANCE_TABLE = "create table " + MAINTENANCE_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + MAINTENANCE_TITLE + " text, "
                + MAINTENANCE_DESCRIPTION + " text, "
                + MAINTENANCE_MILEAGE + " integer, "
                + MAINTENANCE_PRICE + " integer, "
                + MAINTENANCE_DATE + " text, "
                + MAINTENANCE_NOTIFICATIONS + " integer, "
                + MAINTENANCE_CAR_ID + " integer " + " ) ";
        sqLiteDatabase.execSQL(CREATE_CAR_TABLE);
        sqLiteDatabase.execSQL(CREATE_SERVICE_ENTRY);
        sqLiteDatabase.execSQL(CREATE_MAINTENANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + CAR_TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + SERVICE_ENTRY_TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + MAINTENANCE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
        if (findAllCars().size() == 0) {
            car.setActive(1);
        }
        car.setTimestamp(Calendar.getInstance().getTime().getTime());
        String ADD_CAR = "insert into " + CAR_TABLE +
                " values(null, '"
                + car.getManufacturer() + "', '"
                + car.getModel() + "', '"
                + car.getShape()+ "', '"
                + car.getEngine() + "', '"
                + car.getPower() + "', '"
                + car.getYear() + "', '"
                + car.getVin() + "', '"
                + car.getMileage() + "', '"
                + car.getFuelType() + "', '"
                + car.getLicenseNo() + "', '"
                + car.getActive() + "', '"
                + car.getTimestamp() + "', '"
                + car.getImage() + "') ";
        database.execSQL(ADD_CAR);
        return true;
    }

    public List<Car> findAllCars() {
        SQLiteDatabase database = getReadableDatabase();
        String FIND_ALL_CARS = "select * from " + CAR_TABLE;
        Cursor cursor = database.rawQuery(FIND_ALL_CARS, null);
        List<Car> cars = new ArrayList<>();

        while (cursor.moveToNext()) {
            cars.add(buildCarFromCursor(cursor));
        }
        cursor.close();
        return cars;
    }

    public void updateCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_CAR = "update " + CAR_TABLE + " set " +
                CAR_MANUFACTURER + " = '" + car.getManufacturer() + "', " +
                CAR_MODEL + " = '" + car.getModel() + "', " +
                CAR_SHAPE + " = '" + car.getShape() + "', " +
                CAR_ENGINE + " = '" + car.getEngine() + "', " +
                CAR_POWER + " = " + car.getPower() + ", " +
                CAR_YEAR + " = " + car.getYear() + ", " +
                CAR_VIN + " = '" + car.getVin() + "', " +
                CAR_MILEAGE + " = " + car.getMileage() + ", " +
                CAR_FUEL + " = '" + car.getFuelType() + "', " +
                CAR_LICENSE_NO + " = '" + car.getLicenseNo() + "', " +
                CAR_ACTIVE + " = " + car.getActive() + ", " +
                CAR_TIMESTAMP + " = " + car.getTimestamp() + ", " +
                CAR_IMAGE + " = " + car.getImage() +
                " where " + ID + " = " + car.getId();
        database.execSQL(UPDATE_CAR);
        database.close();
    }

    public void updateCarSetActive(Long id, int active) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_CAR_SET_ACTIVE = "update " + CAR_TABLE +
                " set " + CAR_ACTIVE + " = " + active +
                " where " + ID + " = " + id;
        database.execSQL(UPDATE_CAR_SET_ACTIVE);
        database.close();
    }

    public void deleteCar(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_CAR = "delete from " + CAR_TABLE +
                " where id = " + id;
        database.execSQL(DELETE_CAR);
        database.close();
    }

    public boolean checkLicenseNoUniqueConstraint(Car car, boolean isEdit) {
        List<Car> carList = findAllCars();
        if (isEdit) {
            for (Car carFromDb : carList) {
                if (!carFromDb.getId().equals(car.getId()) &&
                        carFromDb.getLicenseNo().toLowerCase().equals(car.getLicenseNo().toLowerCase())) {
                    return false;
                }
            }
        } else {
            for (Car carFromDb : carList) {
                if (carFromDb.getLicenseNo().toLowerCase().equals(car.getLicenseNo().toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Car getActiveCar() {
        SQLiteDatabase database = getWritableDatabase();
        String GET_ACTIVE_CAR = "select * from " + CAR_TABLE +
                " where " + CAR_ACTIVE + " = " + 1;
        Cursor cursor = database.rawQuery(GET_ACTIVE_CAR, null);
        if (cursor.moveToNext()) {
           Car car = buildCarFromCursor(cursor);
           cursor.close();
           return car;
        }
        cursor.close();
        return null;
    }

    public List<ServiceEntry> findAllServiceEntriesByCarId(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_SERVICE_ENTRIES_BY_CAR_ID = "select * from " + SERVICE_ENTRY_TABLE +
                " where " + SERVICE_ENTRY_CAR_ID + " = " + id;
        Cursor cursor = database.rawQuery(FIND_ALL_SERVICE_ENTRIES_BY_CAR_ID, null);
        List<ServiceEntry> serviceEntries = new ArrayList<>();

        while (cursor.moveToNext()) {
            serviceEntries.add(buildServiceEntryFromCursor(cursor));
        }
        cursor.close();
        return serviceEntries;
    }

    public void addServiceEntry(ServiceEntry serviceEntry) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_SERVICE_ENTRY = "insert into " + SERVICE_ENTRY_TABLE +
                " values(null, '"
                + serviceEntry.getTitle() + "', '"
                + serviceEntry.getDescription() + "', '"
                + serviceEntry.getMileage() + "', '"
                + serviceEntry.getPrice() + "', '"
                + serviceEntry.getDate() + "', '"
                + serviceEntry.getCarId() + "')";
        database.execSQL(ADD_SERVICE_ENTRY);

    }

    private ServiceEntry buildServiceEntryFromCursor(Cursor cursor) {
        ServiceEntry serviceEntry = new ServiceEntry();
        serviceEntry.setId(cursor.getLong(0));
        serviceEntry.setTitle(cursor.getString(1));
        serviceEntry.setDescription(cursor.getString(2));
        serviceEntry.setMileage(cursor.getInt(3));
        serviceEntry.setPrice(cursor.getDouble(4));
        serviceEntry.setDate(cursor.getString(5));
        serviceEntry.setCarId(cursor.getLong(6));
        return serviceEntry;
    }

    private Car buildCarFromCursor(Cursor cursor) {
        Car car = new Car();
        car.setId(cursor.getLong(0));
        car.setManufacturer(cursor.getString(1));
        car.setModel(cursor.getString(2));
        car.setShape(cursor.getString(3));
        car.setEngine(cursor.getString(4));
        car.setPower(cursor.getInt(5));
        car.setYear(cursor.getInt(6));
        car.setVin(cursor.getString(7));
        car.setMileage(cursor.getInt(8));
        car.setFuelType(cursor.getString(9));
        car.setLicenseNo(cursor.getString(10));
        car.setActive(cursor.getInt(11));
        car.setTimestamp(cursor.getLong(12));
        car.setImage(cursor.getInt(13));
        return car;
    }

    public void deleteServiceEntriesForCarId(Long carId) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_CAR = "delete from " + SERVICE_ENTRY_TABLE +
                " where " + SERVICE_ENTRY_CAR_ID + " = " + carId;
        database.execSQL(DELETE_CAR);
        database.close();
    }

    public void deleteServiceEntry(Long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String DELETE_SERVICE_ENTRY = "delete from " + SERVICE_ENTRY_TABLE +
                " where " + ID + " = " + id;
        sqLiteDatabase.execSQL(DELETE_SERVICE_ENTRY);
    }

    public void addMaintenance(Maintenance maintenance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String ADD_MAINTENANCE = "insert into " + MAINTENANCE_TABLE +
                " values(null, '"
                + maintenance.getTitle() + "', '"
                + maintenance.getDescription() + "', '"
                + maintenance.getMileage() + "', '"
                + maintenance.getPrice() + "', '"
                + maintenance.getDate() + "', '"
                + maintenance.isNotificationActive() + "', '"
                + maintenance.getCarId() + "')";
        sqLiteDatabase.execSQL(ADD_MAINTENANCE);
    }

    public List<Maintenance> findAllMaintenance(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_MAINTENANCE_BY_CAR_ID = "select * from " + MAINTENANCE_TABLE +
                " where " + MAINTENANCE_CAR_ID + " = " + id;
        Cursor cursor = database.rawQuery(FIND_ALL_MAINTENANCE_BY_CAR_ID, null);
        List<Maintenance> maintenanceList = new ArrayList<>();

        while (cursor.moveToNext()) {
            maintenanceList.add(buildMaintenanceFromCursor(cursor));
        }
        cursor.close();
        return maintenanceList;
    }

    private Maintenance buildMaintenanceFromCursor(Cursor cursor) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(cursor.getLong(0));
        maintenance.setTitle(cursor.getString(1));
        maintenance.setDescription(cursor.getString(2));
        maintenance.setMileage(cursor.getInt(3));
        maintenance.setPrice(cursor.getDouble(4));
        maintenance.setDate(cursor.getString(5));
        maintenance.setNotificationActive(cursor.getInt(6));
        maintenance.setCarId(cursor.getLong(7));
        return maintenance;
    }
}
