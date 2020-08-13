/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.traveltour;

import hienht.status.StatusDTO;
import hienht.util.Util;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author thehien
 */
public class TravelTourDTO implements Serializable{

    private int tourId, quota, leftQuota;
    private String toLocation, tourName, imageLink;
    private Timestamp fromDate, toDate, importedDate;
    private float price;
    private StatusDTO status;

    public TravelTourDTO() {
    }

    public TravelTourDTO(int quota, String toLocation, String tourName, String imageLink, Timestamp fromDate, Timestamp toDate, float price) {
        this.quota = quota;
        this.toLocation = toLocation;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
    }

    
    
    public TravelTourDTO(int tourId, int quota, int leftQuota, String toLocation, String tourName, String imageLink, Timestamp fromDate, Timestamp toDate, Timestamp importedDate, float price, StatusDTO status) {
        this.tourId = tourId;
        this.quota = quota;
        this.leftQuota = leftQuota;
        this.toLocation = toLocation;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.importedDate = importedDate;
        this.price = price;
        this.status = status;
    }

    public int getLeftQuota() {
        return leftQuota;
    }

    public void setLeftQuota(int leftQuota) {
        this.leftQuota = leftQuota;
    }

    
    
    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
    }

    public float getPrice() {
        return price;
    }
    
    public String getPriceDisplay() {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(price);
        
    }

    
    public String getFromDateDisplay(){
        return Util.displayDate(fromDate);
    }
    public String getToDateDisplay(){
        return Util.displayDate(toDate);
    }
    public String getImportedDateDisplay(){
        return Util.displayDate(importedDate);
    }
    
    

    public void setPrice(float price) {
        this.price = price;
    }

}
