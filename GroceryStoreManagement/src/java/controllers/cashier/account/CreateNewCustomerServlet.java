/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.account;

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
import models.customer.CustomerDAO;
import models.customer.CustomerDTO;
import models.customer.CustomerErrObj;

/**
 *
 * @author quan6
 */
@WebServlet(name = "CreateNewCustomerServlet", urlPatterns = {"/CreateNewCustomerServlet"})
public class CreateNewCustomerServlet extends HttpServlet {

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
            String phone_no = request.getParameter("phone_no");
            String name = request.getParameter("name");
            CustomerErrObj err_obj = new CustomerErrObj();
            if (phone_no.matches("[0-9]+") == false) {
                err_obj.setHas_Error(true);
                err_obj.setPhone_noError("Số điện thoại không được chứa kí tự đặc biệt");
            } else {
                CustomerDAO cDAO = new CustomerDAO();
                CustomerDTO cDTO = cDAO.GetCustomerByPhone(phone_no);

                if (cDTO == null) {
                    cDAO.CreateNewCustomer(phone_no, name);
                } else {
                    err_obj.setHas_Error(true);
                    err_obj.setPhone_noError("Số điện thoại đã tồn tại!");
                }
            }
            Gson gson = new Gson();
            String JSONString = gson.toJson(err_obj);
            out.print(JSONString);
            out.flush();
        } catch (SQLException e) {
            log("SQLException " + e.getMessage());
        } catch (NamingException e) {
            log("NamingException " + e.getMessage());
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
