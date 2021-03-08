package com.repairshop.validator;

import com.repairshop.entity.Customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CustomerValidator {
    public void validate(Customer customer) {
        if (customer.getRegistrationDate() != null) {
            if(customer.getCustomerOriginDate().isAfter(customer.getRegistrationDate())){
               //throw exception ()
            }

        }

        if(customer.getCustomerType()!= null && customer.getCustomerType().equalsIgnoreCase("p")){
            long years = customer.getCustomerOriginDate().until(LocalDate.now(), ChronoUnit.YEARS);
            if(years < 18){
                //throw exception private customers must be over 18 yo
            }
        }

       if (customer.getPhoneNumber().toString().length() != 9){
           //throw exception
       }
    }
}
