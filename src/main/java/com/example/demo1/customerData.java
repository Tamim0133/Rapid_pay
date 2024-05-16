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

    public void customerData(String firstName, String lastName, String gender, String phoneNum, String dob, String nid, String password , String email){
        this.firstName = firstName ;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.dob = dob;
        this.nid = nid;
        this.password = password;
        this.email = email;
    }

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
}

