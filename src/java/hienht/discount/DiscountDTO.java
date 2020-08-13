/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.discount;

import java.io.Serializable;
import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author thehien
 */
public class DiscountDTO implements Serializable{
    private int id;
    private String code;
    private Date expiredDate;
    private float value;

    public DiscountDTO(int id){
        this.id = id;
    }
    
    public DiscountDTO(int id, String code, Date expiredDate, float value) {
        this.id = id;
        this.code = code;
        this.expiredDate = expiredDate;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public float getValue() {
        return value;
    }
    
    public String getValueDisplay(){
        DecimalFormat formatter = new DecimalFormat("###");
        return "-" + formatter.format(value) + " (%)";
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Discount: " + code + " - " + value + " (%)";
    }
    
    
    
}
