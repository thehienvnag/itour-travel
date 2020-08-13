/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.bookingitem;

import hienht.traveltour.TravelTourDAO;
import hienht.traveltour.TravelTourDTO;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class BookingItemDTO implements Serializable {

    private int id, bookingId, travelTourId, amount;
    private TravelTourDTO travelTour;

    public BookingItemDTO(int id, int bookingId, int travelTourId, int amount) throws SQLException, NamingException {
        this.id = id;
        this.bookingId = bookingId;
        this.travelTourId = travelTourId;
        this.amount = amount;
        travelTour = new TravelTourDAO().getTourById(travelTourId);
    }

    
    
    public BookingItemDTO(int travelTourId) throws SQLException, NamingException {
        this.travelTourId = travelTourId;
        this.amount = 1;
        this.travelTour = new TravelTourDAO().getTourById(travelTourId);
    }

    public float getPrice() {
        float totalPrice = this.travelTour.getPrice() * this.amount;
        return totalPrice;
    }

    public String getPriceDisplay() {
        
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(getPrice());
    }

    public TravelTourDTO getTravelTour() {
        return travelTour;
    }

    public void setTravelTour(TravelTourDTO travelTour) {
        this.travelTour = travelTour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTravelTourId() {
        return travelTourId;
    }

    public void setTravelTourId(int travelTourId) {
        this.travelTourId = travelTourId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
