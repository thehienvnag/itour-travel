/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.bookingitem;

import hienht.conn.DBUtilities;
import hienht.traveltour.TravelTourDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class BookingItemDAO {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public Vector isValidBookingItemList(List<BookingItemDTO> items) throws SQLException, NamingException {
        boolean result = true;
        Map<Integer, Integer> itemMap = new HashMap();
        for (BookingItemDTO item : items) {
            Vector data = isValidBookingItem(item);
            boolean isValid = (boolean) data.get(0);
            int remainsQuota = (Integer) data.get(1);
            result &= isValid;
            if (!isValid) {
                itemMap.put(item.getTravelTourId(), remainsQuota);
            }
        }
        Vector data = new Vector();
        data.add(result);
        data.add(itemMap);
        return data;
    }

    public Vector isValidBookingItem(BookingItemDTO item) throws SQLException, NamingException {

        TravelTourDAO travelDAO = new TravelTourDAO();
        int remainingQuota = travelDAO.getLeftQuota(item.getTravelTourId());

        boolean isValid = remainingQuota - item.getAmount() >= 0;
        Vector data = new Vector();
        data.add(isValid);
        data.add(remainingQuota);
        return data;
    }

    public List<BookingItemDTO> getItemsByBookingId(int bookingId) throws SQLException, NamingException {
        List<BookingItemDTO> items = null;

        String sql = "SELECT\n"
                + "	\"Id\",\n"
                + "	\"BookingId\",\n"
                + "	\"TravelTourId\",\n"
                + "	\"Amount\",\n"
                + "	\"TravelTourId\"\n"
                + "FROM \"public\".\"BookingItem\"\n"
                + "WHERE \n"
                + "	\"BookingId\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, bookingId);

            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int tourId = rs.getInt("TravelTourId");
                int amount = rs.getInt("Amount");

                BookingItemDTO item = new BookingItemDTO(id, bookingId, tourId, amount);
                if (items == null) {
                    items = new ArrayList<>();
                }
                items.add(item);
            }
        } finally {
            closeConnection();
        }
        return items;
    }

    public boolean insertBookingItem(BookingItemDTO item, int bookingId) throws SQLException, NamingException {

        boolean result = false;
        String sql = "INSERT INTO \"public\".\"BookingItem\" ( \"BookingId\", \"TravelTourId\", \"Amount\") \n"
                + "VALUES ( ?,?,? );";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, bookingId);
            preStm.setInt(2, item.getTravelTourId());
            preStm.setInt(3, item.getAmount());

            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }
}
