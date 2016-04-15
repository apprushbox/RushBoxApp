package com.rushbox.android.rushboxapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ronner on 12-04-2016.
 */
public class Product implements Serializable {
    private long ID_Product;
    private long ID_Provider;
    private long ID_ProductCategory;
    private String TX_Name;
    private String TX_Description;
    private double NU_Price;
    private double NU_ShippingCost;
    private boolean BO_SpecialOffer;
    private boolean BO_Service;
    private byte[] IM_Image;
    private boolean BO_Active;
    private Date DT_Register;
    private Integer orderAmount = 1;

    public Product(long id_product, String tx_name, String tx_description, long id_provider, byte[] im_image, double nu_price, double nu_shippingCost, boolean bo_service) {
        ID_Product = id_product ;
               ID_Provider = id_provider;
          TX_Name = tx_name;
          TX_Description = tx_description;
          NU_Price = nu_price;
          NU_ShippingCost = nu_shippingCost;
          BO_SpecialOffer = false ;
          BO_Service = bo_service;
          IM_Image = im_image ;
          BO_Active = true ;
    }

    public Product(long ID_Product, long ID_Provider, long ID_ProductCategory,
                   String TX_Name, String TX_Description, double NU_Price, double NU_ShippingCost,
                   boolean BO_SpecialOffer, boolean BO_Service, byte[] IM_Image, boolean BO_Active, Date DT_Register) {
        this.ID_Product = ID_Product;
        this.ID_Provider = ID_Provider;
        this.ID_ProductCategory = ID_ProductCategory;
        this.TX_Name = TX_Name;
        this.TX_Description = TX_Description;
        this.NU_Price = NU_Price;
        this.NU_ShippingCost = NU_ShippingCost;
        this.BO_SpecialOffer = BO_SpecialOffer;
        this.BO_Service = BO_Service;
        this.IM_Image = IM_Image;
        this.BO_Active = BO_Active;
        this.DT_Register = DT_Register;
    }
    
    public Integer getOrderAmount() {
        if (orderAmount==null)
            orderAmount = 1;
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getID_Product() {
        return ID_Product;
    }

    public void setID_Product(long ID_Product) {
        this.ID_Product = ID_Product;
    }

    public long getID_Provider() {
        return ID_Provider;
    }

    public void setID_Provider(long ID_Provider) {
        this.ID_Provider = ID_Provider;
    }

    public long getID_ProductCategory() {
        return ID_ProductCategory;
    }

    public void setID_ProductCategory(long ID_ProductCategory) {
        this.ID_ProductCategory = ID_ProductCategory;
    }

    public String getTX_Name() {
        return TX_Name;
    }

    public void setTX_Name(String TX_Name) {
        this.TX_Name = TX_Name;
    }

    public String getTX_Description() {
        return TX_Description;
    }

    public void setTX_Description(String TX_Description) {
        this.TX_Description = TX_Description;
    }

    public double getNU_Price() {
        return NU_Price;
    }

    public void setNU_Price(double NU_Price) {
        this.NU_Price = NU_Price;
    }

    public double getNU_ShippingCost() {
        return NU_ShippingCost;
    }

    public void setNU_ShippingCost(double NU_ShippingCost) {
        this.NU_ShippingCost = NU_ShippingCost;
    }

    public boolean isBO_SpecialOffer() {
        return BO_SpecialOffer;
    }

    public void setBO_SpecialOffer(boolean BO_SpecialOffer) {
        this.BO_SpecialOffer = BO_SpecialOffer;
    }

    public boolean isBO_Service() {
        return BO_Service;
    }

    public void setBO_Service(boolean BO_Service) {
        this.BO_Service = BO_Service;
    }

    public byte[] getIM_Image() {
        return IM_Image;
    }

    public void setIM_Image(byte[] IM_Image) {
        this.IM_Image = IM_Image;
    }

    public boolean isBO_Active() {
        return BO_Active;
    }

    public void setBO_Active(boolean BO_Active) {
        this.BO_Active = BO_Active;
    }

    public Date getDT_Register() {
        return DT_Register;
    }

    public void setDT_Register(Date DT_Register) {
        this.DT_Register = DT_Register;
    }
}
