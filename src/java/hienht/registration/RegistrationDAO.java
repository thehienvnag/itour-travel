/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.registration;

import hienht.conf.Config;
import hienht.conn.DBUtilities;
import hienht.util.Util;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author USER
 */
public class RegistrationDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public RegistrationDAO() {
    }

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

    public RegistrationDTO registerFacebook(String name, String facebookId) throws SQLException, NamingException, UnsupportedEncodingException {
        String sql = "INSERT INTO \"public\".\"Registration\" (\"Name\",\"FacebookId\",\"RoleId\",\"StatusId\") "
                + "         VALUES(?,?,?,?) "
                + "         RETURNING \"Id\"";
        RegistrationDTO result = null;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, name);
            preStm.setString(2, facebookId);
            preStm.setInt(3, Config.ROLE_USER);
            preStm.setInt(4, Config.STAT_ACTIVE);

            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                result = new RegistrationDTO(name, facebookId, id, Config.ROLE_USER, Config.STAT_ACTIVE);
            }

        } finally {
            closeConnection();
        }
        return result;
    }

    public RegistrationDTO checkLoginFacebook(String facebookId) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "SELECT\n"
                    + "	\"Id\",\n"
                    + "	\"Name\",\n"
                    + "	\"RoleId\"\n"
                    + "FROM \"public\".\"Registration\"\n"
                    + "WHERE \n"
                    + "	\"FacebookId\" = ?  AND \"StatusId\" = 1;";
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, facebookId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                int roleId = rs.getInt("RoleId");
                String name = rs.getString("Name");

                result = new RegistrationDTO(name, facebookId, id, roleId, Config.STAT_ACTIVE);
            }
        } finally {
            closeConnection();
        }
        return result;

    }

    public RegistrationDTO checkLogin(String username, String password) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "SELECT\n"
                    + "	\"Id\",\n"
                    + "	\"Name\",\n"
                    + "	\"StatusId\",\n"
                    + "	\"RoleId\"\n"
                    + "FROM \"public\".\"Registration\"\n"
                    + "WHERE \n"
                    + "	\"Username\" = ? AND\n"
                    + "	\"Password\" = ? AND\n"
                    + "	\"StatusId\" = 1;";
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, Util.encryptPassword(password));
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                int roleId = rs.getInt("RoleId");
                String name = rs.getString("Name");

                result = new RegistrationDTO(username, null, name, id, roleId);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
