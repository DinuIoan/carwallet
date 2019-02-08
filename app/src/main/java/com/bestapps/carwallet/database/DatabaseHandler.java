package com.bestapps.carwallet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Currency;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.model.ServiceEntry;
import com.bestapps.carwallet.model.TripData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carwalletdb";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";

    private static final String CURRENCY_TABLE = "currency_table";
    private static final String CURRENCY = "currency";

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
    private static final String SERVICE_ENTRY_SERVICE_NAME = "service_name";
    private static final String SERVICE_ENTRY_MILEAGE = "mileage";
    private static final String SERVICE_ENTRY_PRICE = "price";
    private static final String SERVICE_ENTRY_DATE = "date";
    private static final String SERVICE_ENTRY_YEAR = "year";
    private static final String SERVICE_ENTRY_MONTH = "month";
    private static final String SERVICE_ENTRY_DAY = "day";
    private static final String SERVICE_ENTRY_CAR_ID = "car_id";
    private static final String TIMESTAMP = "timestamp";

    private static final String MAINTENANCE_TABLE = "maintenance";
    private static final String MAINTENANCE_TITLE= "title";
    private static final String MAINTENANCE_DESCRIPTION = "description";
    private static final String MAINTENANCE_MILEAGE = "mileage";
    private static final String MAINTENANCE_PRICE = "price";
    private static final String MAINTENANCE_DATE = "date";
    private static final String MAINTENANCE_HOUR = "hour";
    private static final String MAINTENANCE_MIN = "min";
    private static final String MAINTENANCE_NOTIFICATIONS = "notification_active";
    private static final String MAINTENANCE_CAR_ID = "car_id";

    private static final String TRIP_TABLE = "trip";
    private static final String TRIP_FROM = "from_location";
    private static final String TRIP_TO = "to_location";
    private static final String TRIP_AVARAGE_CONSUMPTION = "avarage_consumption";
    private static final String TRIP_DISTANCE = "distance";
    private static final String TRIP_TOTAL_PRICE = "total_price";
    private static final String TRIP_FUEL_PRICE = "fuel_price";
    private static final String TRIP_TOTAL_LITERS = "total_liters";

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
                + SERVICE_ENTRY_SERVICE_NAME + " text, "
                + SERVICE_ENTRY_MILEAGE + " integer, "
                + SERVICE_ENTRY_PRICE + " integer, "
                + SERVICE_ENTRY_DATE + " text, "
                + SERVICE_ENTRY_YEAR + " integer, "
                + SERVICE_ENTRY_MONTH + " integer, "
                + SERVICE_ENTRY_DAY + " integer, "
                + SERVICE_ENTRY_CAR_ID + " integer, "
                + TIMESTAMP + " integer " +
                " ) ";

        String CREATE_MAINTENANCE_TABLE = "create table " + MAINTENANCE_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + MAINTENANCE_TITLE + " text, "
                + MAINTENANCE_DESCRIPTION + " text, "
                + MAINTENANCE_MILEAGE + " integer, "
                + MAINTENANCE_PRICE + " integer, "
                + MAINTENANCE_DATE + " text, "
                + MAINTENANCE_HOUR + " text, "
                + MAINTENANCE_MIN + " text, "
                + MAINTENANCE_NOTIFICATIONS + " integer, "
                + MAINTENANCE_CAR_ID + " integer, "
                + TIMESTAMP + " integer " +
                " ) ";
        String CREATE_TRIP_TABLE = "create table " + TRIP_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + TRIP_FROM + " text, "
                + TRIP_TO + " text, "
                + TRIP_AVARAGE_CONSUMPTION + " integer, "
                + TRIP_DISTANCE + " integer, "
                + TRIP_FUEL_PRICE + " integer, "
                + TRIP_TOTAL_PRICE + " integer, "
                + TRIP_TOTAL_LITERS + " integer, "
                + TIMESTAMP + " integer"
                + " ) ";

        String CREATE_CURRENCY_TABLE = "create table " + CURRENCY_TABLE +
                " ( "
                + ID + " integer primary key autoincrement, "
                + CURRENCY + " text"
                + " ) ";
        sqLiteDatabase.execSQL(CREATE_CAR_TABLE);
        sqLiteDatabase.execSQL(CREATE_SERVICE_ENTRY);
        sqLiteDatabase.execSQL(CREATE_MAINTENANCE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TRIP_TABLE);
        sqLiteDatabase.execSQL(CREATE_CURRENCY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + CAR_TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + SERVICE_ENTRY_TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + MAINTENANCE_TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + CURRENCY_TABLE);
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

    public Car findCarById(Long id) {
        SQLiteDatabase database = getReadableDatabase();
        String FIND_ALL_CARS = "select * from " + CAR_TABLE +
                " where id = " + id;
        Cursor cursor = database.rawQuery(FIND_ALL_CARS, null);
        Car car = new Car();

        if (cursor.moveToNext()) {
            car = buildCarFromCursor(cursor);
        }
        cursor.close();
        database.close();
        return car;
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
        database.close();
        return null;
    }

    public List<ServiceEntry> findAllServiceEntriesByCarId(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_SERVICE_ENTRIES_BY_CAR_ID = "select * from " + SERVICE_ENTRY_TABLE +
                " where " + SERVICE_ENTRY_CAR_ID + " = " + id;
        Cursor cursor = database.rawQuery(FIND_ALL_SERVICE_ENTRIES_BY_CAR_ID, null);
        List<ServiceEntry> serviceEntries = new ArrayList<>();

        while (cursor != null && cursor.moveToNext()) {
            serviceEntries.add(buildServiceEntryFromCursor(cursor));
        }
        cursor.close();
        database.close();
        return serviceEntries;
    }

    public List<ServiceEntry> findAllServiceEntries() {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_SERVICE_ENTRIES = "select * from " + SERVICE_ENTRY_TABLE;
        Cursor cursor = database.rawQuery(FIND_ALL_SERVICE_ENTRIES, null);
        List<ServiceEntry> serviceEntries = new ArrayList<>();

        while (cursor.moveToNext()) {
            serviceEntries.add(buildServiceEntryFromCursor(cursor));
        }
        cursor.close();
        database.close();
        return serviceEntries;
    }

    public List<ServiceEntry> findAllServiceEntriesByYearAndCarId(Long carId, Integer year) {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_SERVICE_ENTRIES = "select * from " + SERVICE_ENTRY_TABLE +
                " where " + SERVICE_ENTRY_CAR_ID + " = " + carId +
                " and " + SERVICE_ENTRY_YEAR + " = " + year;
        Cursor cursor = database.rawQuery(FIND_ALL_SERVICE_ENTRIES, null);
        List<ServiceEntry> serviceEntries = new ArrayList<>();

        while (cursor.moveToNext()) {
            serviceEntries.add(buildServiceEntryFromCursor(cursor));
        }
        cursor.close();
        database.close();
        return serviceEntries;
    }

    public void addServiceEntry(ServiceEntry serviceEntry) {
        SQLiteDatabase database = getWritableDatabase();
        String[] splittedDate = serviceEntry.getDate().split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int day = Integer.parseInt(splittedDate[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,
                calendar.getTime().getHours(),
                calendar.getTime().getMinutes(),
                calendar.getTime().getSeconds());
        long timestamp = calendar.getTimeInMillis();
        String ADD_SERVICE_ENTRY = "insert into " + SERVICE_ENTRY_TABLE +
                " values(null, '"
                + serviceEntry.getTitle() + "', '"
                + serviceEntry.getDescription() + "', '"
                + serviceEntry.getServiceName() + "', '"
                + serviceEntry.getMileage() + "', '"
                + serviceEntry.getPrice() + "', '"
                + serviceEntry.getDate() + "', '"
                + serviceEntry.getYear() + "', '"
                + serviceEntry.getMonth() + "', '"
                + serviceEntry.getDay() + "', '"
                + serviceEntry.getCarId() + "', '"
                + timestamp + "')";
        database.execSQL(ADD_SERVICE_ENTRY);
        database.close();
    }

    private ServiceEntry buildServiceEntryFromCursor(Cursor cursor) {
        ServiceEntry serviceEntry = new ServiceEntry();
        serviceEntry.setId(cursor.getLong(0));
        serviceEntry.setTitle(cursor.getString(1));
        serviceEntry.setDescription(cursor.getString(2));
        serviceEntry.setServiceName(cursor.getString(3));
        serviceEntry.setMileage(cursor.getInt(4));
        serviceEntry.setPrice(cursor.getDouble(5));
        serviceEntry.setDate(cursor.getString(6));
        serviceEntry.setYear(cursor.getInt(7));
        serviceEntry.setMonth(cursor.getInt(8));
        serviceEntry.setDay(cursor.getInt(9));
        serviceEntry.setCarId(cursor.getLong(10));
        serviceEntry.setTimestamp(cursor.getLong(11));
        return serviceEntry;
    }

    public void updateServiceEntry(ServiceEntry serviceEntry) {
        SQLiteDatabase database = getWritableDatabase();
        String[] splittedDate = serviceEntry.getDate().split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int day = Integer.parseInt(splittedDate[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, getRandomInt(),getRandomInt(), getRandomInt());
        long timestamp = calendar.getTimeInMillis();
        String UPDATE_SERVICE_ENTRY = "update " + SERVICE_ENTRY_TABLE + " set " +
                SERVICE_ENTRY_TITLE + " = '" + serviceEntry.getTitle() + "', " +
                SERVICE_ENTRY_DESCRIPTION + " = '" + serviceEntry.getDescription() + "', " +
                SERVICE_ENTRY_SERVICE_NAME + " = '" + serviceEntry.getServiceName() + "', " +
                SERVICE_ENTRY_MILEAGE + " = " + serviceEntry.getMileage()+ ", " +
                SERVICE_ENTRY_PRICE+ " = " + serviceEntry.getPrice() + ", " +
                SERVICE_ENTRY_DATE+ " = '" + serviceEntry.getDate() + "', " +
                SERVICE_ENTRY_YEAR+ " = " + serviceEntry.getYear() + ", " +
                SERVICE_ENTRY_MONTH+ " = " + serviceEntry.getMonth() + ", " +
                SERVICE_ENTRY_DAY + " = " + serviceEntry.getDay() + ", " +
                SERVICE_ENTRY_CAR_ID + " = " + serviceEntry.getCarId() + ", " +
                TIMESTAMP + " = " + timestamp +
                " where " + ID + " = " + serviceEntry.getId();
        database.execSQL(UPDATE_SERVICE_ENTRY);
        database.close();
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
        sqLiteDatabase.close();
    }

    public void addMaintenance(Maintenance maintenance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] splittedDate = maintenance.getDate().split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int day = Integer.parseInt(splittedDate[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, Integer.parseInt(maintenance.getHour())
                ,Integer.parseInt(maintenance.getMin()), calendar.getTime().getSeconds());
        long timestamp = calendar.getTimeInMillis();
        String ADD_MAINTENANCE = "insert into " + MAINTENANCE_TABLE +
                " values(null, '"
                + maintenance.getTitle() + "', '"
                + maintenance.getDescription() + "', '"
                + maintenance.getMileage() + "', '"
                + maintenance.getPrice() + "', '"
                + maintenance.getDate() + "', '"
                + maintenance.getHour() + "', '"
                + maintenance.getMin() + "', '"
                + maintenance.isNotificationActive() + "', '"
                + maintenance.getCarId() + "', '"
                + timestamp + "')";
        sqLiteDatabase.execSQL(ADD_MAINTENANCE);
        sqLiteDatabase.close();
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
        database.close();
        return maintenanceList;
    }

    public List<Maintenance> findAllMaintenance() {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_MAINTENANCE = "select * from " + MAINTENANCE_TABLE;
        Cursor cursor = database.rawQuery(FIND_ALL_MAINTENANCE, null);
        List<Maintenance> maintenanceList = new ArrayList<>();

        while (cursor.moveToNext()) {
            maintenanceList.add(buildMaintenanceFromCursor(cursor));
        }
        cursor.close();
        database.close();
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
        maintenance.setHour(cursor.getString(6));
        maintenance.setMin(cursor.getString(7));
        maintenance.setNotificationActive(cursor.getInt(8));
        maintenance.setCarId(cursor.getLong(9));
        maintenance.setTimestamp(cursor.getLong(10));
        return maintenance;
    }

    public void deleteMaintenance(Long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String DELETE_MAINTENANCE = "delete from " + MAINTENANCE_TABLE +
                " where " + ID + " = " + id;
        sqLiteDatabase.execSQL(DELETE_MAINTENANCE);
        sqLiteDatabase.close();
    }

    public void updateMaintenance(Maintenance maintenance) {
        SQLiteDatabase database = getWritableDatabase();
        String[] splittedDate = maintenance.getDate().split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int day = Integer.parseInt(splittedDate[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, Integer.parseInt(maintenance.getHour())
                ,Integer.parseInt(maintenance.getMin()), getRandomInt());
        long timestamp = calendar.getTimeInMillis();
        String UPDATE_MAINTENANCE = "update " + MAINTENANCE_TABLE + " set " +
                MAINTENANCE_TITLE + " = '" + maintenance.getTitle() + "', " +
                MAINTENANCE_DESCRIPTION + " = '" + maintenance.getDescription() + "', " +
                MAINTENANCE_MILEAGE + " = " + maintenance.getMileage()+ ", " +
                MAINTENANCE_PRICE + " = " + maintenance.getPrice() + ", " +
                MAINTENANCE_DATE+ " = '" + maintenance.getDate()+ "', " +
                MAINTENANCE_HOUR+ " = '" + maintenance.getHour()+ "', " +
                MAINTENANCE_MIN+ " = '" + maintenance.getMin()+ "', " +
                MAINTENANCE_CAR_ID + " = " + maintenance.getCarId() +
                TIMESTAMP + " = " + timestamp +
                " where " + ID + " = " + maintenance.getId();
        database.execSQL(UPDATE_MAINTENANCE);
        database.close();
    }

    public void addTrip(TripData tripData) {
        SQLiteDatabase database = getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        long timestamp = calendar.getTimeInMillis();

        String ADD_TRIP = "insert into " + TRIP_TABLE +
                " values(null, '"
                + tripData.getFromLocation() + "', '"
                + tripData.getToLocation() + "', '"
                + tripData.getAvarageConsumption() + "', '"
                + tripData.getDistance() + "', '"
                + tripData.getFuelPrice() + "', '"
                + tripData.getTotalPrice() + "', '"
                + tripData.getTotalLiters() + "', '"
                + timestamp + "')";
        database.execSQL(ADD_TRIP);
        database.close();
    }

    public List<TripData> findAllTrips() {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_ALL_TRIPS = "select * from " + TRIP_TABLE;
        Cursor cursor = database.rawQuery(FIND_ALL_TRIPS, null);
        List<TripData> tripDataList = new ArrayList<>();

        while (cursor.moveToNext()) {
            tripDataList.add(buildTripFromCursor(cursor));
        }
        database.close();
        cursor.close();
        return tripDataList;
    }

    public void deleteTripData(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_TRIP = "delete from " + TRIP_TABLE+
                " where " + ID + " = " + id;
        database.execSQL(DELETE_TRIP);
        database.close();
    }

    private TripData buildTripFromCursor(Cursor cursor) {
        TripData tripData = new TripData();
        tripData.setId(cursor.getLong(0));
        tripData.setFromLocation(cursor.getString(1));
        tripData.setToLocation(cursor.getString(2));
        tripData.setAvarageConsumption(cursor.getDouble(3));
        tripData.setDistance(cursor.getDouble(4));
        tripData.setFuelPrice(cursor.getDouble(5));
        tripData.setTotalPrice(cursor.getDouble(6));
        tripData.setTotalLiters(cursor.getDouble(7));
        tripData.setTimestamp(cursor.getLong(8));
        return tripData;
    }

    public void addCurrency(String currency) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_CURRENCY = "insert into " + CURRENCY_TABLE +
                " values(0, '" + currency + "')";
        database.execSQL(ADD_CURRENCY);
        database.close();
    }

    public Currency findCurrency() {
        SQLiteDatabase database = getWritableDatabase();
        String FIND_CURRENCY = "select * from " + CURRENCY_TABLE + " where id = 0";
        Cursor cursor = database.rawQuery(FIND_CURRENCY, null);
        Currency currency = new Currency();

        if (cursor.moveToFirst()) {
            currency.setId(0);
            currency.setCurrency(cursor.getString(1));
            database.close();
            cursor.close();
            return currency;
        } else {
            return null;
        }
    }

    public void updateCurrency(String s) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_CURRENCY = "update " + CURRENCY_TABLE+ " set " +
                CURRENCY + " = '" + s + "' " + " where " + ID + " = 0";
        database.execSQL(UPDATE_CURRENCY);
        database.close();
    }

    private int getRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
