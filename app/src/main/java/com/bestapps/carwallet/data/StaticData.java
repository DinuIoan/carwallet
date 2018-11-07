package com.bestapps.carwallet.data;

import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.CarType;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    private static List<CarType> carTypes;
    private static List<Car> cars;

    public static List<CarType> getCarTypes() {
        if (carTypes == null) {
            carTypes = new ArrayList<>();
        }
        return carTypes;
    }

    public static void setCarTypes(List<CarType> cars) {
        StaticData.carTypes = cars;
    }

    public static List<Car> getCars() {
        if (cars == null) {
            cars = new ArrayList<>();
        }
        return cars;
    }

    public static void setCars(List<Car> cars) {
        StaticData.cars = cars;
    }
}
