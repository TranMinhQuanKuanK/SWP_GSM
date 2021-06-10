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
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.product.ProductDAO;
import models.product.ProductDTO;
import models.sessionBill.BillErrObj;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "EditQuantityBillServlet", urlPatterns = {"/EditQuantityBillServlet"})
public class EditQuantityBillServlet extends HttpServlet {

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
            Integer new_quantity = Integer.parseInt(request.getParameter("quantity"));
            BillObj bill = (BillObj) session.getAttribute("BILL");
            ArrayList<BillItemObject> details = bill.getBill_Detail();
            ProductDAO pDAO = new ProductDAO();
            ProductDTO pDTO = pDAO.GetProductByID(product_id);

            for (int i = 0; i < details.size(); i++) {
                if (details.get(i).getProduct().getProduct_ID() == product_id) {
                    if (new_quantity >= 1) {
                        if (new_quantity <= pDTO.getQuantity()) {
                            //sửa Giá total cost 
                            Integer quantity_difference = new_quantity - bill.getBill_Detail().get(i).getQuantity();
                            Integer currentPrice = bill.getBill_Detail().get(i).getProduct().getSelling_price();
                            bill.setTotal_cost(bill.getTotal_cost() + quantity_difference * currentPrice);
                            //sửa quantity trong details
                            details.get(i).setQuantity(new_quantity);
                            //set lại lỗi
                            bill.setErr_obj(new BillErrObj());
                        } else {
                            //Set lỗi
                            BillErrObj bill_error = new BillErrObj();
                            bill_error.appendError("\"" + pDTO.getName() + "\" chỉ còn " + pDTO.getQuantity() + " sản phẩm trong kho");
                            bill_error.setHasError(true);
                            bill.setErr_obj(bill_error);
                            //set lại số lượng = quantity
                            new_quantity = pDTO.getQuantity();
                            //sửa giá total cost
                            Integer quantity_difference = new_quantity - bill.getBill_Detail().get(i).getQuantity();
                            Integer currentPrice = bill.getBill_Detail().get(i).getProduct().getSelling_price();
                            bill.setTotal_cost(bill.getTotal_cost() + quantity_difference * currentPrice);
                            //sửa quantity trong details
                            details.get(i).setQuantity(new_quantity);
                        }
                    }
                }
            }
            session.setAttribute("BILL", bill);

            Gson gson = new Gson();
            String billJSONString = gson.toJson(bill);
            out.print(billJSONString);
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
