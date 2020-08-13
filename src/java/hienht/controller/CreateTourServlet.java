/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.conf.Config;
import hienht.traveltour.TravelTourDAO;
import hienht.traveltour.TravelTourDTO;
import hienht.util.Logger;
import hienht.util.Util;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author thehien
 */
//        (fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//    maxFileSize = 1024 * 1024 * 50, // 50MB
//    maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "CreateTourServlet", urlPatterns = {"/CreateTourServlet"})
@MultipartConfig
public class CreateTourServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = Config.JSP_CREATE_TOUR;
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory itemFactory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(itemFactory);
                List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
                Map<String, String> paramsMap = Util.getParamsMap(items);
                String txtHasCreated = paramsMap.get("txtHasCreated");
                if (txtHasCreated == null) {
                    
                    String txtLocation = paramsMap.get("txtLocation");
                    String txtTourName = paramsMap.get("txtTourName");
                    String txtPrice = paramsMap.get("txtPrice");
                    String txtQuota = paramsMap.get("txtQuota");
                    String txtDateStart = paramsMap.get("txtDateStart");
                    String txtDateEnd = paramsMap.get("txtDateEnd");

                    float price = Util.getFloat(txtPrice, 100000);
                    int quota = Util.getInt(txtQuota, 0, 1);
                    Date dateStart = Util.getDate(txtDateStart);
                    Date dateEnd = Util.getDateAfter(txtDateEnd, dateStart);

                    FileItem fileItem = Util.getFileItem(items);
                    String imageLink = null;
                    if (fileItem != null) {
                        String ext = Util.getFileExtension(fileItem.getName());
                        String randomAppendStr = UUID.randomUUID().toString().substring(1, 23);
                        imageLink = "img/" + randomAppendStr + ext;
                        String uploadPath = request.getServletContext().getRealPath("/") + "img" + File.separator + randomAppendStr + ext;
                        File fileUpload = new File(uploadPath);
                        fileItem.write(fileUpload);
                    }

                    TravelTourDTO travelTour = new TravelTourDTO(quota, txtLocation, txtTourName, imageLink, Util.getTimestampFromDate(dateStart), Util.getTimestampFromDate(dateEnd), price);

                    TravelTourDAO travelTourDAO = new TravelTourDAO();
                    boolean rs = travelTourDAO.insertTour(travelTour);
                    if (rs) {
                        request.setAttribute(Config.ATTR_IMG_LINK, imageLink);
                        request.setAttribute(Config.ATTR_TOUR_NAME, txtTourName);
                        request.setAttribute(Config.ATTR_LOCATION, txtLocation);
                        request.setAttribute(Config.ATTR_QUOTA, txtQuota);
                        request.setAttribute(Config.ATTR_PRICE, txtPrice);
                        request.setAttribute(Config.ATTR_DATE_START, txtDateStart);
                        request.setAttribute(Config.ATTR_DATE_END, txtDateEnd);
                    }
                }
            }
        } catch (FileUploadException ex) {
            Logger.log(ex.getMessage(), CreateTourServlet.class);
        } catch (Exception ex) {
            Logger.log(ex.getMessage(), CreateTourServlet.class);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
