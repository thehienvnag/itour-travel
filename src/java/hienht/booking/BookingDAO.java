/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.booking;

import hienht.conf.Config;
import hienht.conn.DBUtilities;
import hienht.util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class BookingDAO {

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

    public boolean confirmBooking(BookingDTO booking) {
        return true;
    }

    public BookingDTO getBookingByCode(String bookingCode) throws SQLException, NamingException {
        BookingDTO booking = null;
        String sql = "SELECT\n"
                + "	\"Id\",\n"
                + "	\"UserId\",\n"
                + "	\"DiscountId\",\n"
                + "	\"BookingCode\",\n"
                + "	\"StatusId\",\n"
                + "	\"PaymentType\",\n"
                + "	\"ImportedDate\"\n"
                + "FROM \"public\".\"Booking\"\n"
                + "WHERE \n"
                + "	\"BookingCode\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, bookingCode);

            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int userId = rs.getInt("UserId");
                int discountId = rs.getInt("DiscountId");
                int statusId = rs.getInt("StatusId");
                String paymentType = rs.getString("PaymentType");
                booking = new BookingDTO(id, userId, discountId, bookingCode, statusId, importedDate, paymentType);
            }
        } finally {
            closeConnection();
        }
        return booking;

    }

    public List<BookingDTO> getBookingByUserId(int userId) throws SQLException, NamingException {
        List<BookingDTO> list = null;
        String sql = "SELECT\n"
                + "	\"Id\",\n"
                + "	\"UserId\",\n"
                + "	\"DiscountId\",\n"
                + "	\"BookingCode\",\n"
                + "	\"StatusId\",\n"
                + "	\"PaymentType\",\n"
                + "	\"ImportedDate\"\n"
                + "FROM \"public\".\"Booking\"\n"
                + "WHERE \n"
                + "	\"UserId\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userId);

            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int discountId = rs.getInt("DiscountId");
                int statusId = rs.getInt("StatusId");
                String bookingCode = rs.getString("BookingCode");
                String paymentType = rs.getString("PaymentType");
                BookingDTO booking = new BookingDTO(id, userId, discountId, bookingCode, statusId, importedDate, paymentType);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(booking);
            }
        } finally {
            closeConnection();
        }
        return list;

    }

    public List<BookingDTO> getPendingBookings() throws SQLException, NamingException {
        List<BookingDTO> list = null;
        String sql = "SELECT\n"
                + "	\"Id\",\n"
                + "	\"UserId\",\n"
                + "	\"DiscountId\",\n"
                + "	\"BookingCode\",\n"
                + "	\"StatusId\",\n"
                + "	\"PaymentType\",\n"
                + "	\"ImportedDate\"\n"
                + "FROM \"public\".\"Booking\"\n"
                + "WHERE \n"
                + "	\"StatusId\" = 2;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);

            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int userId = rs.getInt("UserId");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int discountId = rs.getInt("DiscountId");
                int statusId = rs.getInt("StatusId");
                String bookingCode = rs.getString("BookingCode");
                String paymentType = rs.getString("PaymentType");
                BookingDTO booking = new BookingDTO(id, userId, discountId, bookingCode, statusId, importedDate, paymentType);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(booking);
            }
        } finally {
            closeConnection();
        }
        return list;

    }

    public BookingDTO getBookingById(int id) throws SQLException, NamingException {
        BookingDTO res = null;
        String sql = "SELECT\n"
                + "	\"Id\",\n"
                + "	\"UserId\",\n"
                + "	\"DiscountId\",\n"
                + "	\"BookingCode\",\n"
                + "	\"StatusId\",\n"
                + "	\"PaymentType\",\n"
                + "	\"ImportedDate\"\n"
                + "FROM \"public\".\"Booking\"\n"
                + "WHERE \n"
                + "	\"Id\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);

            rs = preStm.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("UserId");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int discountId = rs.getInt("DiscountId");
                int statusId = rs.getInt("StatusId");
                String bookingCode = rs.getString("BookingCode");
                String paymentType = rs.getString("PaymentType");
                res = new BookingDTO(id, userId, discountId, bookingCode, statusId, importedDate, paymentType);

            }
        } finally {
            closeConnection();
        }
        return res;

    }

    public String updateBookingCode(int bookingId) throws SQLException, NamingException {

        Date now = new Date(System.currentTimeMillis());
        String code = "VN" + Util.getDateAsStringDetail(now) + "-" + bookingId;
        String result = null;
        String sql = "UPDATE \"public\".\"Booking\" SET\n"
                + "	\"BookingCode\" = ?\n"
                + "WHERE\n"
                + "	         \"Id\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, code);
            preStm.setInt(2, bookingId);

            boolean res = preStm.executeUpdate() > 0;
            if (res) {
                result = code;
            }
        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean updateBookingType(int bookingId, String paymentType) throws SQLException, NamingException {

        boolean result = false;
        String sql = "UPDATE \"public\".\"Booking\" SET\n"
                + "	\"PaymentType\" = ?\n"
                + "WHERE\n"
                + "	         \"Id\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, paymentType);
            preStm.setInt(2, bookingId);

            result = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean updateBookingTypeByCode(String bookingCode, String paymentType) throws SQLException, NamingException {

        boolean result = false;
        String sql = "UPDATE \"public\".\"Booking\" SET\n"
                + "	\"PaymentType\" = ?\n"
                + "WHERE\n"
                + "	\"BookingCode\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, paymentType);
            preStm.setString(2, bookingCode);

            result = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean updateBookingStatus(String bookingCode) throws SQLException, NamingException {

        boolean result = false;
        String sql = "UPDATE \"public\".\"Booking\" SET\n"
                + "	   \"StatusId\" = ?\n"
                + "WHERE\n"
                + "	\"BookingCode\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, Config.STAT_ACTIVE);
            preStm.setString(2, bookingCode);

            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean enactiveBooking(int bookingId) throws SQLException, NamingException {

        boolean result = false;
        String sql = "UPDATE \"public\".\"Booking\" SET\n"
                + "	   \"StatusId\" = ?\n"
                + "WHERE\n"
                + "	\"Id\" = ?;";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, Config.STAT_ACTIVE);
            preStm.setInt(2, bookingId);

            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public int insertBooking(BookingDTO booking) throws SQLException, NamingException {

        int idInsert = -1;
        int discountId = booking.getDiscountId();
        Date current = new Date(System.currentTimeMillis());

        String sql = "INSERT INTO \"public\".\"Booking\" ( \"UserId\", \"DiscountId\", \"StatusId\", \"PaymentType\", \"ImportedDate\") \n"
                + "VALUES (?,?,?,?,?) RETURNING \"Id\";";

        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            
            preStm.setInt(1, booking.getUserId());
            if (discountId == -1) {
                preStm.setNull(2, Types.INTEGER);
            } else {
                preStm.setInt(2, discountId);
            }
            preStm.setInt(3, booking.getStatusId());
            preStm.setString(4, booking.getPaymentType());
            preStm.setDate(5, current);
            rs = preStm.executeQuery();

            if (rs.next()) {
                idInsert = rs.getInt("Id");
            }

        } finally {
            closeConnection();
        }

        return idInsert;
    }

}
