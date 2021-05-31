/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.authorization;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.account.AccountDAO;
import models.account.AccountDTO;
import models.account.LoginErrObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private String LOGIN_PAGE = "login.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Accces!!");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = LOGIN_PAGE;
        try {
            System.out.println("i went here");
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            AccountDAO aDAO = new AccountDAO();
            AccountDTO accountDTO = aDAO.CheckLogin(username, password);
            LoginErrObj loginErrObj = null;
            if (username == null && password == null) {
                url = LOGIN_PAGE;
            } else if (accountDTO == null) {
                loginErrObj = new LoginErrObj("Username or password is incorrect! Please try again");
            } else if (accountDTO.isIs_owner()) {
                loginErrObj = new LoginErrObj(false, accountDTO.isIs_owner() ? 1 : 2);
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_STATUS", 1);
                session.setAttribute("USERNAME", accountDTO.getUsername());
                session.setAttribute("FULLNAME", accountDTO.getName());
            } else if (!accountDTO.isIs_owner()) {
                loginErrObj = new LoginErrObj(false, accountDTO.isIs_owner() ? 1 : 2);
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_STATUS", 2);
                session.setAttribute("USERNAME", accountDTO.getUsername());
                session.setAttribute("FULLNAME", accountDTO.getName());
            }
            Gson gson = new Gson();
            String errorJSONString = gson.toJson(loginErrObj);
            out.print(errorJSONString);

//(custDTO == null && adminName == null) {
//                url = LOGIN_PAGE;
//                request.setAttribute("LoginErrorMessage", "Username or Password incorrect!!");
//            } else if (custDTO != null) {
//                url = CUSTOMER_MAIN_PAGE;
//                HttpSession session = request.getSession();
//                session.setAttribute("FULLNAME", custDTO.getCustFullname());
//                session.setAttribute("ADDRESS", custDTO.getCustAddress());
//                session.setAttribute("USERNAME", username);
//                session.setAttribute("USER_TYPE", "Customer");
//            } else if (adminName != null) {
//                url = ADMIN_MAIN_PAGE;
//                HttpSession session = request.getSession();
//                session.setAttribute("FULLNAME", adminName);
//                 session.setAttribute("USERNAME", username);
//                session.setAttribute("USER_TYPE", "Admin");
//            }
        } catch (SQLException ex) {
            log("LoginServlet SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet NamingException: " + ex.getMessage());
        } finally {
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
