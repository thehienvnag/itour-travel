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
@WebServlet(name = "FinishPayMomoServlet", urlPatterns = {"/FinishPayMomoServlet"})
public class FinishPayMomoServlet extends HttpServlet {

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
        String txtErrorCode = request.getParameter("errorCode");
        String bookingCode = request.getParameter("requestId");
        String url = Config.JSP_AFTER_BOOKING;
        try {
            int errorCode = Util.getInt(txtErrorCode, -1, 0);
            if (bookingCode == null) {
                //do nothing
            } else if (errorCode == 0) {
                BookingDAO bookingDAO = new BookingDAO();
                bookingDAO.updateBookingStatus(bookingCode);
                bookingDAO.updateBookingTypeByCode(bookingCode, "Charged with Momo");
            }
            url = Config.PAGE_FINISH_BOOKING + "?bookingCode=" + bookingCode;

        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), FinishPayMomoServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), FinishPayMomoServlet.class);
        } finally {
            if (url.equals(Config.JSP_AFTER_BOOKING)) {
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
