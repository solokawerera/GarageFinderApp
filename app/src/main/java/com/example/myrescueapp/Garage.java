package com.example.myrescueapp;


import java.util.ArrayList;
import java.util.List;

public class Garage {
    private String garageId;
    private String userid;
    private String name;
    private String location;
    private String phoneNumber;
    private  String email;
    private  String moregaragedetails;
    private String imageResourceId;
   // private Coordinate coordinate;
    private List<GarageService> garageServices=new ArrayList<>();


    public Garage() {
    }

    public Garage(String garageId, String userid, String name,String email, String location, String phoneNumber,String moregaragedetails, String imageResourceId, List<GarageService> garageServices) {
        this.garageId = garageId;
        this.userid = userid;
        this.name = name;
        this.email= email;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.imageResourceId = imageResourceId;
        this.moregaragedetails = moregaragedetails;
//        this.coordinate = coordinate;
        this.garageServices = garageServices;
    }

    public String getGarageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoregaragedetails() {
        return moregaragedetails;
    }

    public void setMoregaragedetails(String moregaragedetails) {
        this.moregaragedetails = moregaragedetails;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public List<GarageService> getGarageServices() {
        return garageServices;
    }

    public void setGarageServices(List<GarageService> garageServices) {
        this.garageServices = garageServices;
    }
}
