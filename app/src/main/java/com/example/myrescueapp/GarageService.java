package com.example.myrescueapp;
import android.os.Parcel;
import android.os.Parcelable;

public class GarageService implements Parcelable {
    String name;
    int chargeRate;
    String chargeRateString;
    boolean availability;
    String serviceImage;


    public GarageService(String name, int chargeRate, String chargeRateString, boolean availability, String serviceImage) {
        this.name = name;
        this.chargeRate = chargeRate;
        this.chargeRateString = chargeRateString;
        this.availability = availability;
        this.serviceImage = serviceImage;
    }

    public GarageService() {
    }

    public static final GarageService[] garageServices = {
            new GarageService("Towing",1000,"/km" ,true,""),
            new GarageService("Tyre Change",500,"/tyre",true,""),
            new GarageService("Towing",1000,"/hr",true,""),
            new GarageService("Engine Check",1000,"/hr",true,""),
            new GarageService("Painting",1000,"/vehicle",true,""),
            new GarageService("Consultancy",300,"/hr",true,""),

    };


    protected GarageService(Parcel in) {
        name = in.readString();
        chargeRate = in.readInt();
        chargeRateString = in.readString();
        availability = in.readByte() != 0;
        serviceImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(chargeRate);
        dest.writeString(chargeRateString);
        dest.writeByte((byte) (availability ? 1 : 0));
        dest.writeString(serviceImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GarageService> CREATOR = new Creator<GarageService>() {
        @Override
        public GarageService createFromParcel(Parcel in) {
            return new GarageService(in);
        }

        @Override
        public GarageService[] newArray(int size) {
            return new GarageService[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(int chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getChargeRateString() {
        return chargeRateString;
    }

    public void setChargeRateString(String chargeRateString) {
        this.chargeRateString = chargeRateString;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    @Override
    public String toString() {
        return "GarageService{" +
                "name='" + name + '\'' +
                ", chargeRate=" + chargeRate +
                ", availability=" + availability +
                ", serviceImage='" + serviceImage + '\'' +
                '}';
    }
}

