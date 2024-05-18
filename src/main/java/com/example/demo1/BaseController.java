package com.example.demo1;

public class BaseController {
    public customerData customer;

    public BaseController(customerData customer) {
        this.customer = customer;
    }

    public customerData getCustomer() {
        return customer;
    }

    public void setCustomer(customerData customer) {
        this.customer = customer;
    }

}
