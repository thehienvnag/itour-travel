/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hienht.controller;

import hienht.conf.Config;
import hienht.registration.RegistrationDAO;
import hienht.registration.RegistrationDTO;
import hienht.util.Logger;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author USER
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String url = Config.JSP_LOGIN;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        
        
        try {
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO userInfo = dao.checkLogin(username, password);
            if (userInfo != null) {
                HttpSession session = request.getSession();
                session.setAttribute(Config.ATTR_USER, userInfo);
                String searchLink = (String) session.getAttribute(Config.ATTR_LAST_SEARCH_URL);
                if(searchLink != null){
                    url = searchLink;
                    session.removeAttribute(Config.ATTR_LAST_SEARCH_URL);
                } else{
                    url = Config.URL_AUTH;
                }
            } else{
                request.setAttribute(Config.ATTR_ERR_LOGIN, "Invalid username or password!");
            }
        } catch (SQLException ex) {
            Logger.log("SQL: " + ex.getMessage(), LoginServlet.class);
        } catch (NamingException ex) {
            Logger.log("Naming: " + ex.getMessage(), LoginServlet.class);
        } finally {
            if(url.equals(Config.JSP_LOGIN)){
                request.getRequestDispatcher(url).forward(request, response);
            } else{
                response.sendRedirect(url);
            }
            
            out.close();
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
