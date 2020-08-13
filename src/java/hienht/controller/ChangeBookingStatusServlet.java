/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.booking.BookingDAO;
import hienht.conf.Config;
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

/**
 *
 * @author thehien
 */
@WebServlet(name = "ChangeBookingStatusServlet", urlPatterns = {"/ChangeBookingStatusServlet"})
public class ChangeBookingStatusServlet extends HttpServlet {

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
        String txtBookingId = request.getParameter("bookingId");
        String url = Config.PAGE_MANAGE_BOOKING;
        boolean result = false;
        try {
            int bookingId = Util.getInt(txtBookingId, -1, 1);
            if (bookingId != -1) {
                BookingDAO bookingDAO = new BookingDAO();
                result = bookingDAO.enactiveBooking(bookingId);

            }
            if (!result) {
                url = Config.JSP_MANAGE_BOOKING;
                Map<Integer, String> errorMap = new HashMap<>();
                errorMap.put(bookingId, "Failed to succeed payment!");
                request.setAttribute(Config.ATTR_ERR_SUCCEED_BOOKING, errorMap);
            }
        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), ChangeBookingStatusServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), ChangeBookingStatusServlet.class);
        } finally {
            if (url.equals(Config.PAGE_MANAGE_BOOKING)) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
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
