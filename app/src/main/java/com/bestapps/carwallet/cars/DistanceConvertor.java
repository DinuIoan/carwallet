package com.bestapps.carwallet.cars;

import com.bestapps.carwallet.model.Car;
import com.bestapps.carwallet.model.Maintenance;
import com.bestapps.carwallet.model.ServiceEntry;

public class DistanceConvertor {

    public DistanceConvertor() {
    }

    public void convert(String type1, String type2, Car car) {
        if (type1.equals("km") && type2.equals("yard")) {
            car.setMileage((int) (car.getMileage() * 1093.6133));
        }
        if (type1.equals("km") && type2.equals("mile")) {
            car.setMileage((int) (car.getMileage() * 0.621371192));
        }
        if (type1.equals("yard") && type2.equals("km")) {
            car.setMileage((int) (car.getMileage() * 0.0009144));
        }
        if (type1.equals("yard") && type2.equals("mile")) {
            car.setMileage((int) (car.getMileage() * 0.000568181818));
        }
        if (type1.equals("mile") && type2.equals("km")) {
            car.setMileage((int) (car.getMileage() * 1.609344));
        }
        if (type1.equals("mile") && type2.equals("yard")) {
            car.setMileage((car.getMileage() * 1760));
        }
    }

    public void convert(String type1, String type2, ServiceEntry serviceEntry) {
        if (serviceEntry.getMileage() != 0 && serviceEntry.getMileage() > 0) {
            if (type1.equals("km") && type2.equals("yard")) {
                serviceEntry.setMileage((int) (serviceEntry.getMileage() * 1093.6133));
            }
            if (type1.equals("km") && type2.equals("mile")) {
                serviceEntry.setMileage((int) (serviceEntry.getMileage() * 0.621371192));
            }
            if (type1.equals("yard") && type2.equals("km")) {
                serviceEntry.setMileage((int) (serviceEntry.getMileage() * 0.0009144));
            }
            if (type1.equals("yard") && type2.equals("mile")) {
                serviceEntry.setMileage((int) (serviceEntry.getMileage() * 0.000568181818));
            }
            if (type1.equals("mile") && type2.equals("km")) {
                serviceEntry.setMileage((int) (serviceEntry.getMileage() * 1.609344));
            }
            if (type1.equals("mile") && type2.equals("yard")) {
                serviceEntry.setMileage((serviceEntry.getMileage() * 1760));
            }
        }
    }

    public void convert(String type1, String type2, Maintenance maintenance) {
        if (maintenance.getMileage() != 0 && maintenance.getMileage() > 0) {
            if (type1.equals("km") && type2.equals("yard")) {
                maintenance.setMileage((int) (maintenance.getMileage() * 1093.6133));
            }
            if (type1.equals("km") && type2.equals("mile")) {
                maintenance.setMileage((int) (maintenance.getMileage() * 0.621371192));
            }
            if (type1.equals("yard") && type2.equals("km")) {
                maintenance.setMileage((int) (maintenance.getMileage() * 0.0009144));
            }
            if (type1.equals("yard") && type2.equals("mile")) {
                maintenance.setMileage((int) (maintenance.getMileage() * 0.000568181818));
            }
            if (type1.equals("mile") && type2.equals("km")) {
                maintenance.setMileage((int) (maintenance.getMileage() * 1.609344));
            }
            if (type1.equals("mile") && type2.equals("yard")) {
                maintenance.setMileage((maintenance.getMileage() * 1760));
            }
        }
    }
}
