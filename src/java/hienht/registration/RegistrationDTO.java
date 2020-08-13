/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.registration;

import hienht.booking.BookingDTO;
import java.io.Serializable;

/**
 *
 * @author USER
 */
public class RegistrationDTO implements Serializable {

    private String username, password, name, facebookId;
    private int id, roleId, statusId;
    private BookingDTO currentBooking;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String name, String facebookId, int id, int roleId, int statusId) {
        this.name = name;
        this.facebookId = facebookId;
        this.id = id;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public RegistrationDTO(String username, String facebookId, String name, int id, int roleId) {
        this.username = username;
        this.facebookId = facebookId;
        this.id = id;
        this.roleId = roleId;
        this.name = name;
    }

    public String getNameLetter() {
        String[] nameParts = name.split(" ");
        return nameParts[nameParts.length - 1].substring(0, 1);
    }

    public int getCurrentBookingAmount(int tourId) {
        int leftQuota = 0;
        if (currentBooking != null) {
            leftQuota = currentBooking.getCurrentTourAmount(tourId);
        }
        return leftQuota;
    }

    public BookingDTO getCurrentBooking() {
        if (currentBooking == null) {
            currentBooking = new BookingDTO(id);
        }
        return currentBooking;
    }

    public void setCurrentBooking(BookingDTO currentBooking) {
        this.currentBooking = currentBooking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}
