package com.example.demo1;

public class cashInType {
    private String time;
    private int amount;

    cashInType(String time , int amount){
        this.time = time;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }
}
