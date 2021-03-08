package com.repairshop.entity;

public class RepairDetail {
    private Long repairDetailId;
    private String repairType;
    private Double price;

    public Long getRepairDetailId() {
        return repairDetailId;
    }

    public String getRepairType() {
        return repairType;
    }

    public Double getPrice() {
        return price;
    }

    public void setRepairDetailId(Long repairDetailId) {
        this.repairDetailId = repairDetailId;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
