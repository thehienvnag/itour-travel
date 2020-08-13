/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.util;

/**
 *
 * @author thehien
 */
public class Logger {
    public static void log(String msg, Class className){
        org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(className);
        LOGGER.fatal(msg);
    }
}
