package com.example.demo1;

public class currentLoggedInCustomer {
    private  customerData customer;
    public  currentLoggedInCustomer()
    {

    }
    public  currentLoggedInCustomer (customerData customer)
    {
        this.customer = customer;
        System.out.println("Found New Logged In Customer !");
    }
    public customerData  getCurrentLoggedInCustomer()
    {
        return  customer;
    }
}


