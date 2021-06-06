/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.category.CategoryDTO;
import models.product.ProductDTO;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "GetBillServlet", urlPatterns = {"/GetBillServlet"})
public class GetBillServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            BillObj bill = null;

            if (session.getAttribute("BILL") == null) {
                //testing
                BillObj testBill = new BillObj();
//                testBill.getBill_Detail().add(new BillItemObject(
//                        new ProductDTO(98, "TestProduct1 asd asdasd asd asd ád", 1000, 1010, 500, 5, new CategoryDTO(1, "TestCategory", "info"),
//                                "chai", true, "no where"), 5));
//                testBill.getBill_Detail().add(new BillItemObject(
//                        new ProductDTO(99, "TestProduct2 adsasd asd asd asd asd asd ", 2230, 2340, 2340, 6, new CategoryDTO(1, "TestCategory2", "info"),
//                                "chai", true, "no where"), 2));
                bill = testBill;
                session.setAttribute("BILL", testBill);
               
            } else {
                bill = (BillObj) session.getAttribute("BILL");
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
