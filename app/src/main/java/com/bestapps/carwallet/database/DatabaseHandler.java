package com.bestapps.carwallet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bestapps.carwallet.model.Car;

import java.sql.SQLData;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        sqLiteDatabase.execSQL(CREATE_CAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + CAR_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
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
        database.close();
    }

    public List<Car> findAllCars() {
        SQLiteDatabase database = getReadableDatabase();
        String FIND_ALL_CARS = "select * from " + CAR_TABLE;
        Cursor cursor = database.rawQuery(FIND_ALL_CARS, null);
        List<Car> cars = new ArrayList<>();

        while (cursor.moveToNext()) {
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
            cars.add(car);
        }
        cursor.close();
        database.close();
        return cars;
    }

    public void updateCarSetActive(Long id, int active) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_CAR_SET_ACTIVE = "update " + CAR_TABLE +
                " set " + CAR_ACTIVE + " = " + active +
                " where " + ID + " = " + id;
        database.execSQL(UPDATE_CAR_SET_ACTIVE);
        database.close();
    }
}
