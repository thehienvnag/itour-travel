/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.conn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.FileItem;

/**
 *
 * @author thehien
 */
public class Util {
    
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

}
