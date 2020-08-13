/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.booking;

import hienht.bookingitem.BookingItemDAO;
import hienht.bookingitem.BookingItemDTO;
import hienht.discount.DiscountDAO;
import hienht.discount.DiscountDTO;
import hienht.util.Util;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class BookingDTO implements Serializable {

    private int id, userId, discountId;
    private String bookingCode, paymentType;
    private int statusId;
    private Timestamp importedDate;
    private List<BookingItemDTO> items;
    private DiscountDTO discount;

    public BookingDTO(int id, int userId, int discountId, String bookingCode, int statusId, Timestamp importedDate, String paymentType) throws SQLException, NamingException {
        this.id = id;
        this.userId = userId;
        this.discountId = discountId;
        this.bookingCode = bookingCode;
        this.statusId = statusId;
        this.importedDate = importedDate;
        this.paymentType = paymentType;
        items = new BookingItemDAO().getItemsByBookingId(id);
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getImportDateDisplay() {
        return Util.displayDate(importedDate);
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setDiscount(DiscountDTO discount) {
        if (discount == null) {
            this.discountId = -1;
        }

        this.discount = discount;
    }

    public BookingDTO() {
    }

    public BookingDTO(int userId) {
        this.userId = userId;
        this.discountId = -1;
        this.statusId = 2;
    }

    public int getCurrentTourAmount(int tourId) {
        BookingItemDTO item = findItem(tourId);
        if (item == null) {
            return 0;
        }
        return item.getAmount();
    }

    public BookingDTO(int id, int userId, int discountId, Timestamp importedDate) {
        this.id = id;
        this.userId = userId;
        this.discountId = discountId;
        this.importedDate = importedDate;
    }

    private void increaseAmount(int travelTourId) {
        for (BookingItemDTO item : items) {
            if (item.getTravelTourId() == travelTourId) {
                item.setAmount(item.getAmount() + 1);
            }
        }
    }

    private BookingItemDTO findItem(int travelTourId) {
        if (items == null) {
            return null;
        }
        for (BookingItemDTO item : items) {
            if (item.getTravelTourId() == travelTourId) {
                return item;
            }
        }
        return null;
    }

    private boolean isExistedTravel(int travelTourId) {
        return findItem(travelTourId) != null;
    }

    public void updateItem(int travelTourId, int amount) {

        if (travelTourId == -1) {
            return;
        }
        if (amount == -1) {
            return;
        }
        if (amount == 0) {
            removeItem(travelTourId);
        }
        
        BookingItemDTO item = findItem(travelTourId);
        if (item != null) {
            item.setAmount(amount);
        }
    }

    public void removeItem(int travelTourId) {

        if (travelTourId == -1) {
            return;
        }
        if (items == null) {
            return;
        }

        BookingItemDTO item = findItem(travelTourId);
        if (item != null) {
            items.remove(item);
        }
    }

    public void addItemToBooking(int travelTourId) throws SQLException, NamingException {
        if (items == null) {
            items = new ArrayList<>();
        }
        if (!isExistedTravel(travelTourId)) {
            items.add(new BookingItemDTO(travelTourId));
        } else {
            increaseAmount(travelTourId);
        }
    }

    public List<BookingItemDTO> getItems() {
        return items;
    }

    public void setItems(List<BookingItemDTO> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) throws SQLException, NamingException {
        this.discountId = discountId;
        discount = new DiscountDAO().getDiscountById(discountId);
    }

    public float getTotalPrice() {
        if (items == null) {
            return 0;
        }
        float total = 0;
        for (BookingItemDTO item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public long getTotalPayment() {
        return (long) (getTotalPrice() - getDiscountPrice());
    }

    public String getTotalPriceDisplay() {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(getTotalPrice() - getDiscountPrice());
    }

    public float getDiscountPrice() {
        if (discount == null) {
            return 0;
        }

        float discountPrice = (float) (getTotalPrice() * ((double) discount.getValue() / 100));
        return discountPrice;
    }

    public String getDiscountPriceDisplay() {
        if (discount != null) {
            DecimalFormat formatter = new DecimalFormat("###,###,###,###");
            return "-" + formatter.format(getDiscountPrice());
        }
        return null;
    }

    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
    }

}
