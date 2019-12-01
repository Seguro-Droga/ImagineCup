package com.hacks.imaginecup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medicine {

    @SerializedName("$class")
    @Expose
    private String $class;
    @SerializedName("medicineID")
    @Expose
    private String medicineID;
    @SerializedName("medicineName")
    @Expose
    private String medicineName;
    @SerializedName("contents")
    @Expose
    private List<String> contents = null;
    @SerializedName("manufactureDate")
    @Expose
    private String manufactureDate;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("owner")
    @Expose
    private String owner;

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
