/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.fb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hienht.netutils.HttpRequestWrapper;
import java.io.IOException;

/**
 *
 * @author thehien
 */
public class FacebookUtil {

    public static final String CLIENT_ID = "266499387906046";
    public static final String CLIENT_SECRET = "bb49d5d8f557250e86cf861105e81d32";
    public static final String REDIRECT_URI = "https://itour-travel.herokuapp.com/login-facebook";
//    public static final String REDIRECT_URI = "http://localhost:8084/TravelTour/login-facebook";
    public static final String LINK_GET_TOKEN = "https://graph.facebook.com/oauth/access_token";
    public static final String LINK_OPEN_DIALOG = "https://www.facebook.com/dialog/oauth";
    public static final String LINK_GET_INFO = "https://graph.facebook.com/me";

    public FacebookUtil() {
    }

    public static String getLinkGetToken(String code) {

        String link = LINK_GET_TOKEN
                + "?client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + code;
        return link;
    }

    public String getLinkOpenLoginDialog() {
        String link = LINK_OPEN_DIALOG
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI;
        return link;
    }

    public static String getAccessToken(String code) throws IOException {
        String linkGetToken = getLinkGetToken(code);
        String response = HttpRequestWrapper.getMethod(linkGetToken);
        
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        String accessToken = json.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static FacebookPojo getUserInfo(String accessToken) throws IOException {

        String link = LINK_GET_INFO
                + "?access_token=" + accessToken;
        String response = HttpRequestWrapper.getMethod(link);
        
        FacebookPojo userInfo = new Gson().fromJson(response, FacebookPojo.class);
        return userInfo;
    }

}
