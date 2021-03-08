package com.repairshop.entity;

import java.time.LocalDate;

public class Repair {
    private Long repairId;
    private Long repairOldId;
    private Vehicle vehicle;
    private RepairDetail repairDetail;
    private RepairShop repairShop;
    private LocalDate billDate;

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public void setRepairOldId(Long repairOldId) {
        this.repairOldId = repairOldId;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setRepairDetail(RepairDetail repairDetail) {
        this.repairDetail = repairDetail;
    }

    public void setRepairShop(RepairShop repairShop) {
        this.repairShop = repairShop;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public Long getRepairId() {
        return repairId;
    }

    public Long getRepairOldId() {
        return repairOldId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public RepairDetail getRepairDetail() {
        return repairDetail;
    }

    public RepairShop getRepairShop() {
        return repairShop;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairId=" + repairId +
                ", repairOldId=" + repairOldId +
                ", vehicle=" + vehicle +
                ", repairDetail=" + repairDetail +
                ", repairShop=" + repairShop +
                ", billDate=" + billDate +
                '}';
    }
}
