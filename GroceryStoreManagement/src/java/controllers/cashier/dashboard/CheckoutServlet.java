/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.customer.CustomerDTO;
import models.sessionBill.BillDAO;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {

            BillObj billObj = (BillObj) request.getSession().getAttribute("BILL");

//            private ArrayList<BillItemObject> Bill_Detail;
//    private String phone_no;
//    private Timestamp buy_date;
//    private String cashier_username;
//    private int total_cost;
//   // private int point_used;
//    private int cash;
//    boolean use_point; //thêm
//    CustomerDTO customer_dto; //thêm
//            String phone_no, Timestamp buy_date, String cashier_username,
//            Integer total_cost, Integer point_used, Integer cash, Integer profit
            String phone_no = billObj.getPhone_no();
            Timestamp buy_date = billObj.getBuy_date();
            String cashier_username = billObj.getCashier_username();
            Integer total_cost = billObj.getTotal_cost();
            Integer point_used = Math.min((int) Math.ceil(billObj.getTotal_cost() / 1000), billObj.getCustomer_dto().getPoint()); //???
            Integer cash = Integer.parseInt(request.getParameter("cash"));

            ArrayList<BillItemObject> Bill_Detail = billObj.getBill_Detail();
            Integer profit = 0;
            for (BillItemObject detail : Bill_Detail) {
                profit += detail.getProduct().getSelling_price() - detail.getProduct().getCost_price();
            }

            BillDAO bDAO = new BillDAO();
            Integer Bill_ID = bDAO.CreateBill(phone_no, buy_date, cashier_username, total_cost, point_used, cash, profit);
            for (BillItemObject detail : Bill_Detail) {
                Integer product_id = detail.getProduct().getProduct_ID();
                Integer quantity = detail.getQuantity();
                Integer cost = detail.getProduct().getSelling_price();
                Integer total = cost * quantity;
                bDAO.CreateBillDetail(Bill_ID, product_id, quantity, cost, total);
            }
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
