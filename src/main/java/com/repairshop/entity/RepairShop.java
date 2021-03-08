package com.repairshop.entity;

public class RepairShop {
    private Long repairShopId;
    private String name;
    private String managerName;

    public Long getRepairShopId() {
        return repairShopId;
    }

    public String getName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setRepairShopId(Long repairShopId) {
        this.repairShopId = repairShopId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
