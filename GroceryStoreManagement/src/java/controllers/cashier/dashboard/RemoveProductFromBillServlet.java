/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.sessionBill.BillErrObj;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "RemoveProductFromBillServlet", urlPatterns = {"/RemoveProductFromBillServlet"})
public class RemoveProductFromBillServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            Integer product_id = Integer.parseInt(request.getParameter("product_id"));
            BillObj bill = (BillObj) session.getAttribute("BILL");
            ArrayList<BillItemObject> details = bill.getBill_Detail();
            System.out.println("Toi den RemoveProductFromBillServlet va chuan bi remove" + product_id);
            //lấy vị trí
            int result = -1;
            for (int i = 0; i < details.size(); i++) {
                if (details.get(i).getProduct().getProduct_ID() == product_id) {
                    result = i;
                }
            }
            //xóa
            if (result >= 0) {
                BillItemObject selected_for_remove_Product = details.get(result);
                int price_lost = selected_for_remove_Product.getProduct().getSelling_price()
                        * selected_for_remove_Product.getQuantity();
                details.remove(result);
                bill.setTotal_cost(bill.getTotal_cost() - price_lost);
                bill.setBill_Detail(details);
                //set lại lỗi
                bill.setErr_obj(new BillErrObj());
                session.setAttribute("BILL", bill);
            } else {
                log("Invalid product_id");
            }
            Gson gson = new Gson();
            String billJSONString = gson.toJson(bill);
            out.print(billJSONString);
            out.flush();
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
