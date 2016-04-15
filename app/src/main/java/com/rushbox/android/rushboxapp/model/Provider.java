package com.rushbox.android.rushboxapp.model;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ronner on 12-04-2016.
 */
public class Provider implements Serializable {

    private long ID_Provider;
    private long ID_User;
    private String TX_Name;
    private String TX_AddressLine1;
    private String TX_AddressLine2;
    private int ID_Country;
    private long ID_City;
    private String TX_StateProvinceRegion;
    private String TX_Zip;
    private String TX_PhoneNumber;
    private Date DT_Register;
    private boolean BO_Open;
    private Location GE_Location;
    private double NU_Latitude;
    private double NU_Longitud;
    private double NU_Distance;

    public Provider(long ID_Provider, long ID_User, String TX_Name, String TX_AddressLine1,
                    String TX_AddressLine2, int ID_Country, long ID_City, String TX_StateProvinceRegion,
                    String TX_Zip, String TX_PhoneNumber, Date DT_Register, boolean BO_Open, Location GE_Location) {
        this.ID_Provider = ID_Provider;
        this.ID_User = ID_User;
        this.TX_Name = TX_Name;
        this.TX_AddressLine1 = TX_AddressLine1;
        this.TX_AddressLine2 = TX_AddressLine2;
        this.ID_Country = ID_Country;
        this.ID_City = ID_City;
        this.TX_StateProvinceRegion = TX_StateProvinceRegion;
        this.TX_Zip = TX_Zip;
        this.TX_PhoneNumber = TX_PhoneNumber;
        this.DT_Register = DT_Register;
        this.BO_Open = BO_Open;
        this.GE_Location = GE_Location;
    }

    public double getNU_Distance() {
        return NU_Distance;
    }

    public void setNU_Distance(double NU_Distance) {
        this.NU_Distance = NU_Distance;
    }

    public long getID_Provider() {
        return ID_Provider;
    }

    public void setID_Provider(long ID_Provider) {
        this.ID_Provider = ID_Provider;
    }

    public long getID_User() {
        return ID_User;
    }

    public void setID_User(long ID_User) {
        this.ID_User = ID_User;
    }

    public String getTX_Name() {
        return TX_Name;
    }

    public void setTX_Name(String TX_Name) {
        this.TX_Name = TX_Name;
    }

    public String getTX_AddressLine1() {
        return TX_AddressLine1;
    }

    public void setTX_AddressLine1(String TX_AddressLine1) {
        this.TX_AddressLine1 = TX_AddressLine1;
    }

    public String getTX_AddressLine2() {
        return TX_AddressLine2;
    }

    public void setTX_AddressLine2(String TX_AddressLine2) {
        this.TX_AddressLine2 = TX_AddressLine2;
    }

    public int getID_Country() {
        return ID_Country;
    }

    public void setID_Country(int ID_Country) {
        this.ID_Country = ID_Country;
    }

    public long getID_City() {
        return ID_City;
    }

    public void setID_City(long ID_City) {
        this.ID_City = ID_City;
    }

    public String getTX_StateProvinceRegion() {
        return TX_StateProvinceRegion;
    }

    public void setTX_StateProvinceRegion(String TX_StateProvinceRegion) {
        this.TX_StateProvinceRegion = TX_StateProvinceRegion;
    }

    public String getTX_Zip() {
        return TX_Zip;
    }

    public void setTX_Zip(String TX_Zip) {
        this.TX_Zip = TX_Zip;
    }

    public String getTX_PhoneNumber() {
        return TX_PhoneNumber;
    }

    public void setTX_PhoneNumber(String TX_PhoneNumber) {
        this.TX_PhoneNumber = TX_PhoneNumber;
    }

    public Date getDT_Register() {
        return DT_Register;
    }

    public void setDT_Register(Date DT_Register) {
        this.DT_Register = DT_Register;
    }

    public boolean isBO_Open() {
        return BO_Open;
    }

    public void setBO_Open(boolean BO_Open) {
        this.BO_Open = BO_Open;
    }

    public Location getGE_Location() {
        return GE_Location;
    }

    public void setGE_Location(Location GE_Location) {
        this.GE_Location = GE_Location;
    }
}
