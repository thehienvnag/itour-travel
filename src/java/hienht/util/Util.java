/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.http.fileupload.FileItem;

/**
 *
 * @author thehien
 */
public class Util {
    
    public static String encryptPassword(String pass){
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(pass);
    }

    public static String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    public static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex);
    }

    public static Map<String, String> getParamsMap(List<FileItem> listItem) {
        Map<String, String> mapParams = new HashMap<>();
        for (FileItem fileItem : listItem) {
            if (fileItem.isFormField()) {
                mapParams.put(fileItem.getFieldName(), fileItem.getString());
            }
        }
        return mapParams;
    }

    public static FileItem getFileItem(List<FileItem> listItem) {
        for (FileItem fileItem : listItem) {
            String contentType = fileItem.getContentType();
            if (contentType != null && contentType.contains("image/")) {
                return fileItem;
            }
        }
        return null;
    }

    public static void writeFile(FileItem item) {

    }

    public static String generateSqlComparison(Map<String, Object> params) {
        String comparison = "";
        for (String key : params.keySet()) {
            ParamObject param = (ParamObject) params.get(key);
            if (param.isValid(key)) {
                comparison += param.getSqlPattern();
            }
        }
        return comparison;
    }

    public static void setValueForPrepareStm(PreparedStatement pst, Map<String, Object> params) throws SQLException {

        int i = 1;
        for (String key : params.keySet()) {
            ParamObject param = (ParamObject) params.get(key);
            if (param.isValid(key)) {
                if (param.getValue() instanceof Date) {
                    pst.setDate(i++, (Date) param.getValue());
                }
                if (param.getValue() instanceof String) {
                    pst.setString(i++, (String) param.getValue());
                }
                if (param.getValue() instanceof Float) {
                    pst.setFloat(i++, (Float) param.getValue());
                }
            }
        }
    }

    public static int getInt(String intValue, int defaultValue, int min) {
        int valueReturn = defaultValue;
        try {
            valueReturn = Integer.parseInt(intValue);
            if (valueReturn < min) {
                throw new Exception();
            }
        } catch (Exception e) {
            valueReturn = defaultValue;
        }
        return valueReturn;
    }

    public static float[] getFloatFromPriceRange(String priceRange) {
        float[] prices = new float[2];
        //initial
        prices[0] = -1;
        prices[1] = -1;

        //split string to get prices
        String[] priceValues = priceRange.split(",");

        try {
            prices[0] = getFloat(priceValues[0]);
            prices[1] = getFloat(priceValues[1]);
            if (prices[0] < 0 || prices[1] < 0) {
                throw new Exception();
            }
        } catch (Exception e) {

        }
        //Swap if startPrice bigger than endPrice
        if (prices[1] < prices[0]) {
            float temp = prices[1];
            prices[1] = prices[0];
            prices[0] = temp;
        }

        return prices;
    }

    public static float getFloat(String floatNumber) {
        return getFloat(floatNumber, 0);
    }

    public static float getFloat(String floatNumber, float min) {
        float number = 0;
        try {
            number = Float.parseFloat(floatNumber);
            if (number < min) {
                throw new Exception();
            }
        } catch (Exception e) {
            number = 0;
        }
        return number;
    }

    public static Date getDateAfter(String dateStr, Date dateBefore) {
        Date dateAfter = getDate(dateStr);
        if (dateAfter != null) {
            if (dateBefore == null) {
                return dateAfter;
            }
            if (dateAfter.compareTo(dateBefore) >= 0) {
                return dateAfter;
            }
        }
        return null;
    }

    public static Timestamp getTimestampFromDate(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Date getDate(String dateStr) {
        try {
            return formatDate(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date formatDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = formatter.parse(dateStr);
        return new Date(date.getTime());
    }

    public static Timestamp formatDateTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = formatter.parse(dateStr);
        return new Timestamp(date.getTime());
    }

    public static String getDateAsString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");
        return formatter.format(date);
    }
    public static String getDateAsStringDetail(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyymmddhhmmss");
        return formatter.format(date);
    }

    public static String displayDate(Timestamp time) {
        java.util.Date date = new java.util.Date(time.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(date);
    }

    public static boolean isSomeNotNullParams(String... params) {
        boolean isEmpty = false;
        for (String param : params) {
            isEmpty |= param != null;
        }
        return isEmpty;
    }

    public static boolean isEmptyParams(String... params) {
        boolean isEmpty = true;
        for (String param : params) {
            isEmpty &= !isNotEmptyParam(param);
        }
        return isEmpty;
    }

    public static boolean isNotEmptyParam(String param) {
        return param != null && !param.isEmpty();
    }

}
