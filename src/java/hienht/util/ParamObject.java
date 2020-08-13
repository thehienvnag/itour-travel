/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.util;

import java.sql.Date;

/**
 *
 * @author thehien
 */
public class ParamObject {

    private String sqlPattern;
    private Object value;

    public ParamObject(String sqlPattern, Object value) {
        this.sqlPattern = sqlPattern;
        this.value = value;
    }

    public String getSqlPattern() {
        return sqlPattern;
    }

    public void setSqlPattern(String sqlPattern) {
        this.sqlPattern = sqlPattern;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public boolean isValid(String paramName){
        switch(paramName){
            case "ToLocation":
                return isValidLocation();
            case "DateStart":
                return isValidDateStart();
            case "DateEnd":
                return isValidDateEnd();
            case "PriceStart":
                return isValidPriceStart();
            case "PriceEnd":
                return isValidPriceEnd();
        }
        return false;
    }

    public boolean isValidLocation() {
        String location = (String) value;
        return Util.isNotEmptyParam(location);
    }

    public boolean isValidDateStart() {
        Date dateStart = (Date) value;
        return dateStart != null;
    }

    public boolean isValidDateEnd() {
        Date dateEnd = (Date) value;
        return dateEnd != null;
    }

    public boolean isValidPriceStart() {
        float priceStart = (Float) value;
        return priceStart > 0;
    }

    public boolean isValidPriceEnd() {
        float priceEnd = (Float) value;
        return priceEnd > 0;
    }

}
