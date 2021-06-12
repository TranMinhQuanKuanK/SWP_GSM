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
import java.util.Date;
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

            String phone_no = billObj.getPhone_no();

            Date date = new Date();
            Timestamp buy_date = new Timestamp(date.getTime());

            String cashier_username = (String) request.getSession().getAttribute("USERNAME");

            Integer total_cost = billObj.getTotal_cost();
            Integer point_used = 0;
            if (billObj.isUse_point()) {
                point_used = Math.min((int) Math.ceil(billObj.getTotal_cost() / 1000),
                        billObj.getCustomer_dto().getPoint()); //???
            }

            Integer cash = request.getParameter("cash").length() > 0 ? Integer.parseInt(request.getParameter("cash")) : 0;

            ArrayList<BillItemObject> Bill_Detail = billObj.getBill_Detail();
            Integer profit = 0;
            for (BillItemObject detail : Bill_Detail) {
                System.out.println("Món :" + detail.getProduct().getName() + " có gia ban: " + detail.getProduct().getSelling_price() + " va gia goc: " + detail.getProduct().getCost_price());
                profit += (detail.getProduct().getSelling_price() - detail.getProduct().getCost_price())*detail.getQuantity();
            }
            profit -= point_used * 1000;
            
            System.out.println("---------------------------");
            System.out.println("Bill detail: cashier:" + cashier_username);
            System.out.println("phone no: " + phone_no);
            System.out.println("buy date: " + buy_date);
            System.out.println("total_cost: " + total_cost);
            System.out.println("cash: " + cash);
            System.out.println("profit: " + profit);
            System.out.println("point_used: " + point_used);
            
//            BillDAO bDAO = new BillDAO();
//            Integer Bill_ID = bDAO.CreateBill(phone_no, buy_date, cashier_username, total_cost, point_used, cash, profit);
//            for (BillItemObject detail : Bill_Detail) {
//                Integer product_id = detail.getProduct().getProduct_ID();
//                Integer quantity = detail.getQuantity();
//                Integer cost = detail.getProduct().getSelling_price();
//                Integer total = cost * quantity;
//                bDAO.CreateBillDetail(Bill_ID, product_id, quantity, cost, total);
//            }
        }
//        catch (SQLException e) {
//            log("SQLException " + e.getMessage());
//        } catch (NamingException e) {
//            log("NamingException " + e.getMessage());
//        }
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
