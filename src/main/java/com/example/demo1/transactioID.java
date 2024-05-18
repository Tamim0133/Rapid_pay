package com.example.demo1;

public class transactioID {
    private int amount;
    private String phnNum;

    public transactioID(String phn, int amount){
        this.amount = amount;
        this.phnNum = phn;
    }

    public int getAmount() {
        return amount;
    }

    public String getPhnNum() {
        return phnNum;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPhnNum(String phnNum) {
        this.phnNum = phnNum;
    }
}
