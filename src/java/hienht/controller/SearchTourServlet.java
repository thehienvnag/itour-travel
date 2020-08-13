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
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thehien
 */
@WebServlet(name = "SearchTourServlet", urlPatterns = {"/SearchTourServlet"})
@MultipartConfig
public class SearchTourServlet extends HttpServlet {

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
        String location = request.getParameter("txtLocation");
        String txtPriceRange = request.getParameter("txtPriceRange");
        String txtDateStart = request.getParameter("txtDateStart");
        String txtDateEnd = request.getParameter("txtDateEnd");

        String txtPageIndex = request.getParameter("page");
        String url = Config.JSP_HOME;
        try {
            if (Util.isEmptyParams(location, txtPriceRange, txtDateStart, txtDateEnd)) {
                //Do nothing
            } else {
                //Parse params
                float[] priceValues = Util.getFloatFromPriceRange(txtPriceRange);
                Date dateStart = Util.getDate(txtDateStart);
                Date dateEnd = Util.getDateAfter(txtDateEnd, dateStart);

                //Search in DB
                TravelTourDAO dao = new TravelTourDAO();

                if (location.isEmpty() && priceValues[0] == -1 && priceValues[1] == -1 && dateStart == null && dateEnd == null) {
                    //do nothing
                } else {
                    Vector data = null;
                    if (txtPageIndex == null) {
                        data = dao.searchTravelTour(location, dateStart, dateEnd, priceValues[0], priceValues[1]);
                    } else {
                        int pageIndex = Util.getInt(txtPageIndex, 1, 1);
                        data = dao.searchTravelTour(location, dateStart, dateEnd, priceValues[0], priceValues[1], pageIndex);
                    }

                    int searchRows = (int) data.get(0);
                    int pageSize = Config.PAGE_SIZE;
                    int totalPages = (int) Math.ceil((double) searchRows / pageSize);

                    List<TravelTourDTO> listTravel = (List<TravelTourDTO>) data.get(1);

                    String lastSearch = "search-tour?"
                            + "txtLocation=" + location
                            + "&txtPriceRange=" + txtPriceRange
                            + "&txtDateStart=" + txtDateStart
                            + "&txtDateEnd=" + txtDateEnd;

                    request.setAttribute(Config.ATTR_LAST_SEARCH_URL, lastSearch);
                    request.setAttribute(Config.ATTR_TOTAL_PAGES, totalPages);
                    request.setAttribute(Config.ATTR_TOURS, listTravel);
                    request.setAttribute(Config.ATTR_TOTAL_ROWS, searchRows);
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.log(ex.getMessage(), SearchTourServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), SearchTourServlet.class);
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
