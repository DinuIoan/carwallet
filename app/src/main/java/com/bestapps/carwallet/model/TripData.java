package com.bestapps.carwallet.model;

import java.io.Serializable;

public class TripData implements Serializable{
    private Long id;
    private String fromLocation;
    private String toLocation;
    private double avarageConsumption;
    private double distance;
    private double fuelPrice;
    private double totalPrice;
    private double totalLiters;
    private Long timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public double getAvarageConsumption() {
        return avarageConsumption;
    }

    public void setAvarageConsumption(double avarageConsumption) {
        this.avarageConsumption = avarageConsumption;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalLiters() {
        return totalLiters;
    }

    public void setTotalLiters(double totalLiters) {
        this.totalLiters = totalLiters;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
