package com.bestapps.carwallet.model;

import java.util.ArrayList;
import java.util.List;

public class CarType {
    private String manufacturer;
    private List<String> models = new ArrayList<>();

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }
}
