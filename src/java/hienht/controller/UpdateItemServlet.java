/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.booking.BookingDTO;
import hienht.conf.Config;
import hienht.registration.RegistrationDTO;
import hienht.traveltour.TravelTourDAO;
import hienht.util.Logger;
import hienht.util.Util;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author thehien
 */
@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/UpdateItemServlet"})
public class UpdateItemServlet extends HttpServlet {

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
        String url = Config.PAGE_CART;

        String txtTourId = request.getParameter("txtTourId");
        String txtAmount = request.getParameter("txtAmount");

        try {

            int tourId = Util.getInt(txtTourId, -1, 1);
            int amount = Util.getInt(txtAmount, -1, 0);
            HttpSession session = request.getSession();
            RegistrationDTO userInfo = (RegistrationDTO) session.getAttribute(Config.ATTR_USER);
            BookingDTO booking = userInfo.getCurrentBooking();

            TravelTourDAO tourDAO = new TravelTourDAO();
            int quotaLeft = tourDAO.getLeftQuota(tourId);

            if (quotaLeft < amount) {
                Map<Integer, Integer> mapQuota = new HashMap<>();
                mapQuota.put(tourId, quotaLeft);
                request.setAttribute(Config.ATTR_INVALID_QUOTA, mapQuota);
                url = Config.JSP_CART;
            } else {
                booking.updateItem(tourId, amount);
            }

        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), ManageBookingServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), ManageBookingServlet.class);
        } finally {
            if (url.equals(Config.JSP_CART)) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
            }

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
