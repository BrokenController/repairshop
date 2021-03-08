package com.repairshop.entity;

import java.time.LocalDate;

public class Vehicle {
    private Long vehicleId;
    private Long vehicleOldId;
    private Customer customer;
    private String vin;
    private LocalDate registrationDate;
    private String registrationCountry;
    private String manufacturer;
    private String vehicleType;
    private String vehicleBody;
    private String vehicleEngine;
    private String vehicleRestraint;
    private String model;
    private Long year;
    private String plant;
    private String serialNumber;


    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setVehicleOldId(Long vehicleOldId) {
        this.vehicleOldId = vehicleOldId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setRegistrationCountry(String registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    public void setVehicleEngine(String vehicleEngine) {
        this.vehicleEngine = vehicleEngine;
    }

    public void setVehicleRestraint(String vehicleRestraint) {
        this.vehicleRestraint = vehicleRestraint;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getVehicleOldId() {
        return vehicleOldId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getVin() { return vin; }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getRegistrationCountry() {
        return registrationCountry;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public String getVehicleEngine() {
        return vehicleEngine;
    }

    public String getVehicleRestraint() {
        return vehicleRestraint;
    }

    public String getModel() {
        return model;
    }

    public Long getYear() {
        return year;
    }

    public String getPlant() {
        return plant;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
