/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.staff;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.account.AccountDAO;
import models.account.AccountDTO;
import models.account.AccountErrObj;

/**
 *
 * @author Huu Quoc
 */
@WebServlet(name = "ChangePasswordStoreownerServlet", urlPatterns = {"/ChangePasswordStoreownerServlet"})
public class ChangePasswordStoreownerServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String username = (String) request.getSession().getAttribute("USERNAME");
            AccountErrObj accountErr = new AccountErrObj();
            if (username != null) {
                AccountDAO dao = new AccountDAO();
                AccountDTO aDTO = dao.CheckLogin(username, currentPassword);
                
                if (aDTO == null) {
                    accountErr.setCurrentPasswordError("Mật khẩu hiện tại không đúng");
                    accountErr.setHasError(true);
                } else {
                    if (newPassword.length() < 6 || newPassword.length() > 20) {
                        accountErr.setNewPasswordError("Mật khẩu mới phải có độ dài từ 6 đến 20 kí tự");
                        accountErr.setHasError(true);
                    } else if (newPassword.equals(currentPassword)) {
                        accountErr.setNewPasswordError("Mật khẩu mới phải khác mật khẩu cũ");
                        accountErr.setHasError(true);
                    } else {
                        dao.ChangePassword(username, newPassword);
                    }
                }
                Gson gson = new Gson();
                String JSONString = gson.toJson(accountErr);
                out.print(JSONString);
                out.flush();
            }

        } catch (SQLException e) {
            log("ChangePasswordStoreownerServlet _ SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("ChangePasswordStoreownerServlet _ Naming: " + e.getMessage());
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
