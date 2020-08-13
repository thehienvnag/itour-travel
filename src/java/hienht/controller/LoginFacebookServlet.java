/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.conf.Config;
import hienht.fb.FacebookPojo;
import hienht.fb.FacebookUtil;
import hienht.registration.RegistrationDAO;
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
@WebServlet(name = "LoginFacebookServlet", urlPatterns = {"/LoginFacebookServlet"})
public class LoginFacebookServlet extends HttpServlet {

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
        String code = request.getParameter("code");
        String url = Config.PAGE_HOME;
        boolean isSuccessful = true;
        try {
            String accessToken = FacebookUtil.getAccessToken(code);
            HttpSession session = request.getSession();
            if (accessToken != null) {
                FacebookPojo userInfo = FacebookUtil.getUserInfo(accessToken);

                RegistrationDAO registrationDAO = new RegistrationDAO();
                RegistrationDTO userLoggedIn = registrationDAO.checkLoginFacebook(userInfo.getId());

                if (userLoggedIn == null) {
                    RegistrationDTO userRegistered = registrationDAO.registerFacebook(
                            userInfo.getName(),
                            userInfo.getId()
                    );
                    if (userRegistered != null) {
                        session.setAttribute(Config.ATTR_USER, userRegistered);
                    }
                } else {
                    session.setAttribute(Config.ATTR_USER, userLoggedIn);
                }
            } else {
                isSuccessful = false;
            }

            if (isSuccessful) {
                String searchLink = (String) session.getAttribute(Config.ATTR_LAST_SEARCH_URL);
                if (searchLink != null) {
                    url = searchLink;
                    session.removeAttribute(Config.ATTR_LAST_SEARCH_URL);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            Logger.log(ex.getMessage(), LoginFacebookServlet.class);
        } catch (NamingException ex) {
            Logger.log(ex.getMessage(), LoginFacebookServlet.class);
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
