package com.repairshop.entity;

import java.time.LocalDate;

public class Customer {
    private Long customerId;
    private Long customerOldId;
    private String customerName;
    private String customerType;
    private LocalDate customerOriginDate;
    private LocalDate registrationDate;
    private Long phoneNumber;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerOldId(Long customerOldId) {
        this.customerOldId = customerOldId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setCustomerOriginDate(LocalDate customerOriginDate) {
        this.customerOriginDate = customerOriginDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCustomerOldId() {
        return customerOldId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public LocalDate getCustomerOriginDate() {
        return customerOriginDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
