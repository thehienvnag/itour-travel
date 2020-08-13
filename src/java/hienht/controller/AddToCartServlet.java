/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.booking.BookingDTO;
import hienht.conf.Config;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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
        String txtTourId = request.getParameter("txtTourId");
        String location = request.getParameter("txtLocation");
        String txtPriceRange = request.getParameter("txtPriceRange");
        String txtDateStart = request.getParameter("txtDateStart");
        String txtDateEnd = request.getParameter("txtDateEnd");
        String txtScrollTop = request.getParameter("txtScrollTop");
        String txtPage = request.getParameter("page");
        String url = Config.PAGE_HOME;
        try {
            HttpSession session = request.getSession();
            RegistrationDTO userInfo = (RegistrationDTO) session.getAttribute(Config.ATTR_USER);
            BookingDTO curBooking = userInfo.getCurrentBooking();
            int tourId = Integer.parseInt(txtTourId);

            curBooking.addItemToBooking(tourId);

            session.setAttribute(Config.ATTR_USER, userInfo);
            url = "search-tour?"
                    + "txtLocation=" + location
                    + "&txtPriceRange=" + txtPriceRange
                    + "&txtDateStart=" + txtDateStart
                    + "&txtDateEnd=" + txtDateEnd
                    + "&page=" + txtPage
                    + "&txtScrollTop=" + txtScrollTop;
        } catch (SQLException ex) {
            Logger.log(ex.getMessage(), AddToCartServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), AddToCartServlet.class);
        } finally {
            response.sendRedirect(url);
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
