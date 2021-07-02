/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.importgoods;

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
import models.receipt.ReceiptItem;
import models.receipt.ReceiptObj;

/**
 *
 * @author ROG STRIX
 */
@WebServlet(name = "RemoveFromReceiptServlet", urlPatterns = {"/RemoveFromReceiptServlet"})
public class RemoveFromReceiptServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            Integer product_ID = Integer.parseInt(request.getParameter("product_ID"));
            ReceiptObj receipt = (ReceiptObj)session.getAttribute("RECEIPT");
            ArrayList<ReceiptItem> details = receipt.getReceipt_detail();
            int result = -1;
            for (int i = 0; i < details.size(); i++) {
                if (details.get(i).getProduct().getProduct_ID() == product_ID) {
                    result = i;
                }
            }
            if (result >= 0) {
                ReceiptItem selected_for_remove_Product = details.get(result);
                int price_lost = selected_for_remove_Product.getProduct().getSelling_price()
                        * selected_for_remove_Product.getQuantity();
                details.remove(result);
                receipt.setTotal_cost(receipt.getTotal_cost() - price_lost);
                receipt.setReceipt_detail(details);
                session.setAttribute("RECEIPT", receipt);
            } else {
                System.out.println("INVALID ID");
            }
            Gson gson = new Gson();
            String JSONString = gson.toJson(receipt);
            out.print(JSONString);
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
