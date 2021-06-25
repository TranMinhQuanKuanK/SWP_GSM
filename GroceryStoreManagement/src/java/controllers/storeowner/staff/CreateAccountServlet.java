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
import models.account.AccountErrObj;

/**
 *
 * @author Huu Quoc
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

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

        String name = request.getParameter("new-name");
        String username = request.getParameter("new-username");
        String password = request.getParameter("new-password");
        String confirm = request.getParameter("new-confirm");
        boolean isOwner = (request.getParameter("new-is-owner") != null);
        AccountErrObj err_obj = new AccountErrObj();
        PrintWriter out = response.getWriter();
        
        if (confirm == null) {
            confirm = "";
        }

        try {
            if (username == null) {
                err_obj.setHasError(true);
                err_obj.setUsernameLengthError("Mục này không được để trống!");
            } else if (username.trim().length() < 6 || username.trim().length() > 30) {
                err_obj.setHasError(true);
                err_obj.setUsernameLengthError("Tên đăng nhập phải có độ dài từ 6 đến 30 kí tự!");
            }

            if (password == null) {
                err_obj.setHasError(true);
                err_obj.setPasswordLengthError("Mục này không được để trống!");
            } else if (password.trim().length() < 6 || password.trim().length() > 20) {
                err_obj.setHasError(true);
                err_obj.setPasswordLengthError("Mật khẩu phải có độ dài từ 6 đến 20 kí tự!");
            } else if (!confirm.trim().equals(password.trim())) {
                err_obj.setHasError(true);
                err_obj.setConfirmNotMatch("Không khớp với mật khẩu!");
            }

            if (name == null) {
                err_obj.setHasError(true);
                err_obj.setNameLengthError("Mục này không được để trống!");
            } else if (name.trim().length() < 2 || name.trim().length() > 50) {
                err_obj.setHasError(true);
                err_obj.setNameLengthError("Họ tên phải có độ dài từ 2 đến 50 kí tự!");
            }

            if (!err_obj.getHasError()) {
                AccountDAO dao = new AccountDAO();
                if (dao.checkExist(username)) {
                    err_obj.setHasError(true);
                    err_obj.setUsernameExist("Tên đăng nhập " + username + " đã được sử dụng!");
                } else if (!dao.addAccount(name, username, password, isOwner)) {
                    err_obj.setHasError(true);
                }
            }

            Gson gson = new Gson();
            String JSONString = gson.toJson(err_obj);
            out.print(JSONString);
            out.flush();
        } catch (SQLException ex) {
            log("CreateAccountServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateAccountServlet _ Naming: " + ex.getMessage());
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
