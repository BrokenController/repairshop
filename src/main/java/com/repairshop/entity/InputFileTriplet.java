package com.repairshop.entity;

public class InputFileTriplet {
    private InputFileData customer;
    private InputFileData vehicle;
    private InputFileData repairItem;

    public boolean containsNull(){
        if (this.customer == null || this.vehicle == null || this.repairItem == null){
            return true;
        }return false;
    }

    public void setCustomer(InputFileData customer) {
        this.customer = customer;
    }

    public void setVehicle(InputFileData vehicle) {
        this.vehicle = vehicle;
    }

    public void setRepairItem(InputFileData repairItem) {
        this.repairItem = repairItem;
    }

    public InputFileData getCustomer() {
        return customer;
    }

    public InputFileData getVehicle() {
        return vehicle;
    }

    public InputFileData getRepairItem() {
        return repairItem;
    }
}
