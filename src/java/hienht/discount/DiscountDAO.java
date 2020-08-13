/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.discount;

import hienht.conn.DBUtilities;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author thehien
 */
public class DiscountDAO {

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
    
    public boolean isValidDiscount(int userId, int discountId) throws NamingException, SQLException {
        String sql = "SELECT d.\"Id\" "
                + "FROM \"public\".\"Discount\" d "
                + "WHERE \"ExpiredDate\" > ? AND \"Id\" = ? "
                + "AND (SELECT d.\"Id\" FROM \"public\".\"Booking\" b WHERE b.\"UserId\" = ? AND b.\"DiscountId\" = d.Id) IS NULL";
        boolean result = false;
        Date current = new Date(System.currentTimeMillis());

        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setDate(1, current);
            preStm.setInt(2, discountId);
            preStm.setInt(3, userId);
            rs = preStm.executeQuery();

           if(rs.next()){
               result = true;
           }
             
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<DiscountDTO> getUserDiscounts(int userId) throws NamingException, SQLException {
        String sql = "SELECT d.\"Id\", d.\"Code\", d.\"ExpiredDate\", d.\"Value\" "
                + "FROM \"public\".\"Discount\" d "
                + "WHERE \"ExpiredDate\" > ? "
                + "AND (SELECT b.\"Id\" FROM \"public\".\"Booking\" b WHERE b.\"UserId\" = ? AND b.\"DiscountId\" = d.\"Id\") IS NULL";
        List<DiscountDTO> listDiscount = null;
        Date current = new Date(System.currentTimeMillis());

        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setDate(1, current);
            preStm.setInt(2, userId);
            rs = preStm.executeQuery();

            while (rs.next()) {
                if (listDiscount == null) {
                    listDiscount = new ArrayList<>();
                }
                int id = rs.getInt("Id");
                String code = rs.getString("Code");
                Date expiredDate = rs.getDate("ExpiredDate");
                float value = rs.getFloat("Value");

                listDiscount.add(new DiscountDTO(id, code, expiredDate, value));
            }

        } finally {
            closeConnection();
        }
        return listDiscount;
    }

    public DiscountDTO getDiscountById(int id) throws SQLException, NamingException {
        String sql = "SELECT d.\"Id\", d.\"Code\", d.\"ExpiredDate\", d.\"Value\" "
                + "FROM \"public\".\"Discount\" d "
                + "WHERE d.\"Id\" = ?";
        DiscountDTO discount = null;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if(rs.next()){
                String code = rs.getString("Code");
                Date expiredDate = rs.getDate("ExpiredDate");
                float value = rs.getFloat("Value");

                discount = new DiscountDTO(id, code, expiredDate, value);
            }
            
        } finally {
            closeConnection();
        }
        return discount;
    }
}
