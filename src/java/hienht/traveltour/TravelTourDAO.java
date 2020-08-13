/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.traveltour;

import hienht.bookingitem.BookingItemDTO;
import hienht.conf.Config;
import hienht.conn.DBUtilities;
import hienht.status.StatusDTO;
import hienht.util.ParamObject;
import hienht.util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class TravelTourDAO {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public TravelTourDAO() {
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

    public Vector isValidTourList(List<BookingItemDTO> items) throws SQLException, NamingException {
        boolean result = true;
        Map<Integer, Boolean> mapDate = new HashMap<>();
        for (BookingItemDTO item : items) {
            boolean isValid = isValidTour(item.getTravelTourId());
            result &= isValid;
            if (!isValid) {
                mapDate.put(item.getTravelTourId(), isValid);
            }
        }
        Vector data = new Vector();
        data.add(result);
        data.add(mapDate);
        return data;
    }

    public boolean isValidTour(int tourId) throws SQLException, NamingException {

        String sql = "SELECT\n"
                + "	\"FromDate\"\n"
                + "FROM \"public\".\"TravelTour\"\n"
                + "WHERE \n"
                + "	\"FromDate\" > CURRENT_DATE AND \"TourId\" = ?;";
        boolean res = false;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            rs = preStm.executeQuery();

            res = rs.next();
        } finally {
            closeConnection();
        }
        return res;
    }

    public int getLeftQuota(int tourId) throws SQLException, NamingException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "SELECT t.\"Quota\" - ( SELECT coalesce((SELECT SUM(b.\"Amount\") "
                + "                         FROM \"public\".\"BookingItem\" b "
                + "                         WHERE b.\"TravelTourId\" = t.\"TourId\" "
                + "                         GROUP BY b.\"TravelTourId\"), 0)) as \"LeftQuota\""
                + "     FROM \"public\".\"TravelTour\" t"
                + "     WHERE t.\"TourId\" = ? "
                + "     AND t.\"FromDate\" > ? "
                + "     AND t.\"Quota\" > (SELECT coalesce((SELECT SUM(b.\"Amount\") "
                + "                         FROM \"public\".\"BookingItem\" b "
                + "                         WHERE b.\"TravelTourId\" = t.\"TourId\" "
                + "                         GROUP BY b.\"TravelTourId\"), 0))";
        int leftQuota = -1;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            preStm.setTimestamp(2, now);
            rs = preStm.executeQuery();
            if (rs.next()) {
                leftQuota = rs.getInt("LeftQuota");
            }
        } finally {
            closeConnection();
        }

        return leftQuota;
    }

    public TravelTourDTO getTourById(int tourId) throws SQLException, NamingException {
        String sql = "SELECT \"TourId\" "
                + "      , \"ToLocation\" "
                + "      , \"TourName\" "
                + "      , \"FromDate\" "
                + "      , \"ToDate\" "
                + "      , \"Price\" "
                + "      , \"Quota\" "
                + "      , \"ImageLink\" "
                + "      , \"ImportedDate\" "
                + "      , t.\"Quota\" - (SELECT coalesce((SELECT SUM(b.\"Amount\") "
                + "                             FROM \"public\".\"BookingItem\" b "
                + "                             WHERE b.\"TravelTourId\" = t.\"TourId\" "
                + "                             GROUP BY b.\"TravelTourId\"), 0)) as \"LeftQuota\""
                + "      , (SELECT \"Id\" FROM \"public\".\"Status\" s WHERE s.\"Id\" = t.\"StatusId\") as \"StatusId\" "
                + "      , (SELECT \"Name\" FROM \"public\".\"Status\" s WHERE s.\"Id\" = t.\"StatusId\") as \"StatusName\" "
                + "     FROM \"public\".\"TravelTour\" t"
                + "     WHERE t.\"TourId\" = ?";
        TravelTourDTO tour = null;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String tourName = rs.getString("TourName");
                String toLocation = rs.getString("ToLocation");
                float price = rs.getFloat("Price");
                Timestamp fromDate = rs.getTimestamp("FromDate");
                Timestamp toDate = rs.getTimestamp("ToDate");
                int quota = rs.getInt("Quota");
                int leftQuota = rs.getInt("LeftQuota");
                String imageLink = rs.getString("ImageLink");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int statusId = rs.getInt("StatusId");
                String statusName = rs.getString("StatusName");
                StatusDTO status = new StatusDTO(statusId, statusName);
                tour = new TravelTourDTO(tourId, quota, leftQuota, toLocation, tourName, imageLink, fromDate, toDate, importedDate, price, status);
            }
        } finally {
            closeConnection();
        }
        return tour;
    }

    public Vector searchTravelTour(String location, Date dateStart, Date dateEnd, float priceStart, float priceEnd) throws SQLException, NamingException {
        return searchTravelTour(location, dateStart, dateEnd, priceStart, priceEnd, 1);
    }

    public int countSearchRows(String location, Date dateStart, Date dateEnd, float priceStart, float priceEnd) throws SQLException, NamingException {
        int searchRows = 0;

        Map<String, Object> mapParams = new HashMap<String, Object>() {
            {
                put("ToLocation", new ParamObject(" AND \"ToLocation\" LIKE ?", "%" + location + "%"));
                put("DateStart", new ParamObject(" AND \"FromDate\" >= ?", dateStart));
                put("DateEnd", new ParamObject(" AND \"ToDate\" <= ?", dateEnd));
                put("PriceStart", new ParamObject(" AND \"Price\" >= ?", new Float(priceStart)));
                put("PriceEnd", new ParamObject(" AND \"Price\" <= ?", new Float(priceEnd)));
            }
        };

        String comparison = Util.generateSqlComparison(mapParams);
        String sql = "SELECT COUNT(t.\"TourId\") as \"TotalRows\""
                + "     FROM \"public\".\"TravelTour\" t "
                + "     WHERE t.\"StatusId\" = 1 "
                + comparison;

        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            Util.setValueForPrepareStm(preStm, mapParams);
            rs = preStm.executeQuery();
            if (rs.next()) {
                searchRows = rs.getInt("TotalRows");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return searchRows;
    }

    public Vector searchTravelTour(String location, Date dateStart, Date dateEnd, float priceStart, float priceEnd, int pageIndex) throws SQLException, NamingException {

        Map<String, Object> mapParams = new HashMap<String, Object>() {
            {
                put("ToLocation", new ParamObject(" AND \"ToLocation\" LIKE ?", "%" + location + "%"));
                put("DateStart", new ParamObject(" AND \"FromDate\" >= ?", dateStart));
                put("DateEnd", new ParamObject(" AND \"ToDate\" <= ?", dateEnd));
                put("PriceStart", new ParamObject(" AND \"Price\" >= ?", new Float(priceStart)));
                put("PriceEnd", new ParamObject(" AND \"Price\" <= ?", new Float(priceEnd)));
            }
        };

        int pageSize = Config.PAGE_SIZE;
        int startIndex = (pageIndex - 1) * pageSize;
        String comparison = Util.generateSqlComparison(mapParams);

        String sql = "SELECT DISTINCT ON (\"TourId\") AS \"TourId\" "
                + "      , \"ToLocation\" "
                + "      , \"TourName\" "
                + "      , \"FromDate\" "
                + "      , \"ToDate\" "
                + "      , \"Price\" "
                + "      , \"Quota\" "
                + "      , \"ImageLink\" "
                + "      , \"ImportedDate\" "
                + "      , t.\"Quota\" - (SELECT coalesce((SELECT SUM(b.\"Amount\") "
                + "                             FROM \"public\".\"BookingItem\" b "
                + "                             WHERE b.\"TravelTourId\" = t.\"TourId\" "
                + "                             GROUP BY b.\"TravelTourId\"), 0)) as \"LeftQuota\""
                + "      , (SELECT \"Id\" FROM \"public\".\"Status\" s WHERE s.\"Id\" = t.\"StatusId\") as \"StatusId\" "
                + "      , (SELECT \"Name\" FROM \"public\".\"Status\" s WHERE s.\"Id\" = t.\"StatusId\") as \"StatusName\" "
                + "     FROM \"public\".\"TravelTour\" t "
                + "     WHERE t.\"StatusId\" = 1 "
                + comparison
                + "     ORDER BY t.\"FromDate\""
                + "     OFFSET " + startIndex + " ROWS"
                + "     FETCH NEXT " + pageSize + " ROWS ONLY";

        List<TravelTourDTO> listTravel = null;
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            Util.setValueForPrepareStm(preStm, mapParams);
            rs = preStm.executeQuery();
            while (rs.next()) {
                if (listTravel == null) {
                    listTravel = new ArrayList<>();
                }
                int tourId = rs.getInt("TourId");
                String tourName = rs.getString("TourName");
                String toLocation = rs.getString("ToLocation");
                float price = rs.getFloat("Price");
                Timestamp fromDate = rs.getTimestamp("FromDate");
                Timestamp toDate = rs.getTimestamp("ToDate");
                int quota = rs.getInt("Quota");
                int leftQuota = rs.getInt("LeftQuota");
                String imageLink = rs.getString("ImageLink");
                Timestamp importedDate = rs.getTimestamp("ImportedDate");
                int statusId = rs.getInt("StatusId");
                String statusName = rs.getString("StatusName");
                StatusDTO status = new StatusDTO(statusId, statusName);
                TravelTourDTO travelDTO = new TravelTourDTO(tourId, quota, leftQuota, toLocation, tourName, imageLink, fromDate, toDate, importedDate, price, status);
                listTravel.add(travelDTO);
            }

        } finally {
            closeConnection();
        }

        int searchRows = countSearchRows(location, dateStart, dateEnd, priceStart, priceEnd);
        Vector data = new Vector();
        data.add(searchRows);
        data.add(listTravel);
        return data;
    }

    public boolean insertTour(TravelTourDTO tour) throws SQLException, NamingException {

        boolean result = false;

        String sql = "INSERT INTO \"public\".\"TravelTour\" ("
                + "     \"ToLocation\","
                + "     \"TourName\","
                + "     \"FromDate\","
                + "     \"ToDate\","
                + "     \"Price\","
                + "     \"Quota\","
                + "     \"ImageLink\","
                + "     \"ImportedDate\","
                + "     \"StatusId\")"
                + "     VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtilities.openConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, tour.getToLocation());
            preStm.setString(2, tour.getTourName());
            Timestamp fromDate = new Timestamp(tour.getFromDate().getTime());
            Timestamp toDate = new Timestamp(tour.getToDate().getTime());
            preStm.setTimestamp(3, fromDate);
            preStm.setTimestamp(4, toDate);
            preStm.setFloat(5, tour.getPrice());
            preStm.setInt(6, tour.getQuota());
            preStm.setString(7, tour.getImageLink());
            preStm.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            preStm.setInt(9, Config.STAT_ACTIVE);

            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

}
