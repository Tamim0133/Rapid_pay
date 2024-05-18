package com.example.demo1;

public class customerData {

    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private String dob;
    private String nid;
    private String password;
    private String email;
    private int balance;
    private String imgUri = "";

    public customerData(String firstName, String lastName, String gender,String nid,String dob, String phoneNum, String email,   String password, int balance){
        this.firstName = firstName ;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.dob = dob;
        this.nid = nid;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }
    public void setImgUri(String u){
        imgUri = u;
    }
    public String getImgUri(){return imgUri;}
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getDob(){
        return dob;
    }
    public String getNid(){
        return nid;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public int getBalance(){return balance;}

    public void printInfo()
    {
        System.out.println("Name : " + firstName);
        System.out.println("LastName ; " + lastName);
        System.out.println("Email :" + email);
        System.out.println("Password :" + password);
        System.out.println("Nid : " + nid);
        System.out.println("Phone Num : " + phoneNum);
        System.out.println("Gender : " + gender);
        System.out.println("Date of Birth : " + dob);
        System.out.println("Balance : " + balance);
    }
}

