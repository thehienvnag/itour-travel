/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.booking.BookingDAO;
import hienht.booking.BookingDTO;
import hienht.conf.Config;
import hienht.registration.RegistrationDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author thehien
 */
@WebServlet(name = "PayNowServlet", urlPatterns = {"/PayNowServlet"})
public class PayNowServlet extends HttpServlet {

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
        String url = Config.PAGE_BOOKING_HISTORY;
        try {
            int bookingId = Util.getInt(txtBookingId, -1, 1);
            if (bookingId != -1) {
                BookingDAO bookingDAO = new BookingDAO();
                BookingDTO booking = bookingDAO.getBookingById(bookingId);
                HttpSession session = request.getSession();
                RegistrationDTO user = (RegistrationDTO) session.getAttribute(Config.ATTR_USER);
                if (booking != null && user.getId() == booking.getUserId()) {
                    if (booking.getStatusId() == Config.STAT_DEACTIVE) {
                        request.setAttribute(Config.ATTR_BOOKING, booking);
                        url = Config.CONTROLLER_PAY_MOMO;
                    }
                } else {
                    url = Config.JSP_HOME;
                }

            }
        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), PayNowServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), PayNowServlet.class);
        } finally {
            if (url.equals(Config.PAGE_BOOKING_HISTORY)) {
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
