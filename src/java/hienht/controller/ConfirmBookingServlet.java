/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.booking.BookingDAO;
import hienht.booking.BookingDTO;
import hienht.bookingitem.BookingItemDAO;
import hienht.bookingitem.BookingItemDTO;
import hienht.conf.Config;
import hienht.discount.DiscountDAO;
import hienht.registration.RegistrationDTO;
import hienht.traveltour.TravelTourDAO;
import hienht.util.Logger;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
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
@WebServlet(name = "ConfirmBookingServlet", urlPatterns = {"/ConfirmBookingServlet"})
public class ConfirmBookingServlet extends HttpServlet {

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

        String url = Config.JSP_CART;
        String paymentType = request.getParameter("txtPaymentType");
        try {
            if (paymentType != null) {
                HttpSession session = request.getSession();
                RegistrationDTO userInfo = (RegistrationDTO) session.getAttribute(Config.ATTR_USER);
                BookingDTO curBooking = userInfo.getCurrentBooking();

                if (curBooking.getItems() == null || curBooking.getItems().size() == 0) {
                    //do nothing
                } else {
                    BookingDAO bookingDAO = new BookingDAO();
                    BookingItemDAO bookingItemDAO = new BookingItemDAO();
                    DiscountDAO discountDAO = new DiscountDAO();
                    TravelTourDAO tourDAO = new TravelTourDAO();

                    Vector validItemQuota = bookingItemDAO.isValidBookingItemList(curBooking.getItems());

                    boolean isValid = (boolean) validItemQuota.get(0);
                    if (!isValid) {
                        request.setAttribute(Config.ATTR_INVALID_QUOTA, validItemQuota.get(1));
                    }

                    if (curBooking.getDiscountId() != -1) {
                        boolean isValidDiscount = discountDAO.isValidDiscount(userInfo.getId(), curBooking.getDiscountId());
                        if (!isValidDiscount) {
                            isValid = false;
                            String msg = curBooking.getDiscount() + " has been used!";
                            request.setAttribute(Config.ATTR_INVALID_DISCOUNT, msg);
                            curBooking.setDiscount(null);
                            session.setAttribute(Config.ATTR_USER, userInfo);
                        }

                    }

                    Vector validTourDate = tourDAO.isValidTourList(curBooking.getItems());

                    boolean isValidTour = (boolean) validTourDate.get(0);
                    if (!isValidTour) {
                        isValid = false;
                        request.setAttribute(Config.ATTR_INVALID_TOUR_DATE, validTourDate.get(1));
                    }

                    if (isValid) {
                        int idInsert = bookingDAO.insertBooking(curBooking);

                        if (idInsert != -1) {
                            try {
                                curBooking.setId(idInsert);
                                String bookingCode = bookingDAO.updateBookingCode(idInsert);
                                curBooking.setBookingCode(bookingCode);
                                curBooking.setImportedDate(new Timestamp(System.currentTimeMillis()));

                                if (bookingCode != null) {
                                    for (BookingItemDTO item : curBooking.getItems()) {
                                        bookingItemDAO.insertBookingItem(item, idInsert);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        userInfo.setCurrentBooking(null);
                        session.setAttribute(Config.ATTR_USER, userInfo);
                        String displayType = null;

                        url = Config.JSP_AFTER_BOOKING;
                        if (idInsert == -1) {
                            displayType = "Failed";
                        }
                        if (paymentType != null) {
                            curBooking.setPaymentType(paymentType);
                            bookingDAO.updateBookingType(idInsert, paymentType);
                        }
                        if (paymentType.equals("Charged with Momo")) {
                            url = Config.CONTROLLER_PAY_MOMO;
                        }

                        request.setAttribute(Config.ATTR_BOOKING_STATUS, displayType);
                        request.setAttribute(Config.ATTR_BOOKING, curBooking);
                    }

                }

            } else {
                request.setAttribute(Config.ATTR_ERR_PAYMENT_TYPE, "Please choose payment type to continue..");
            }

        } catch (SQLException ex) {
            ex.getMessage();
            Logger.log(ex.getMessage(), ConfirmBookingServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), ConfirmBookingServlet.class);
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
