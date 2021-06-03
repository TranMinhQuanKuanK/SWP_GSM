/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

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
import javax.servlet.http.HttpSession;
import models.customer.CustomerDAO;
import models.customer.CustomerDTO;
import models.sessionBill.BillObj;

//chưa test
@WebServlet(name = "GetCustomerByPhoneServlet", urlPatterns = {"/GetCustomerByPhoneServlet"})
public class GetCustomerByPhoneServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            CustomerDAO cDAO = new CustomerDAO();
            CustomerDTO cDTO = cDAO.GetCustomerByPhone(request.getParameter("phone_no"));

            HttpSession session = request.getSession();
            BillObj bill = (BillObj) session.getAttribute("BILL");
            if (cDTO != null) {
                bill.setCustomer_dto(cDTO);
                bill.setPhone_no(cDTO.getPhone_no());
            }
            session.setAttribute("BILL", bill);

            Gson gson = new Gson();
            String customerJSONString = gson.toJson(cDTO);
            out.print(customerJSONString);
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
