/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.importgoods;

import com.google.gson.Gson;
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
import models.pendingItem.PendingItemDAO;
import models.pendingItem.PendingItemDTO;
import models.product.ProductDAO;
import models.receipt.ReceiptDAO;
import models.receipt.ReceiptDetailDAO;
import models.receipt.ReceiptItem;
import models.receipt.ReceiptObj;

/**
 *
 * @author ROG STRIX
 */
@WebServlet(name = "MakeNewReceiptServlet", urlPatterns = {"/MakeNewReceiptServlet"})
public class MakeNewReceiptServlet extends HttpServlet {

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
            ReceiptObj receipt = (ReceiptObj) request.getSession().getAttribute("RECEIPT");
            Date date = new Date();
            Timestamp import_date = new Timestamp(date.getTime());
            String username = (String) request.getSession().getAttribute("USERNAME");
            Integer receiptTotal = receipt.getTotal_cost();
            //Them vao ReceiptDB
            ReceiptDAO rDAO = new ReceiptDAO();
            Integer receipt_ID = rDAO.CreateReceipt(import_date, username, receiptTotal);
            
            PendingItemDAO pdDAO = new PendingItemDAO();
            ArrayList<PendingItemDTO> pendingList = pdDAO.GetPendingList();
            ProductDAO pDAO = new ProductDAO();
            ArrayList<ReceiptItem> receiptItems = receipt.getReceipt_detail();
            for (ReceiptItem detail : receiptItems) {
                
                Integer product_id = detail.getProduct().getProduct_ID();
                Integer quantity = detail.getQuantity();
                Integer price = detail.getProduct().getCost_price();
                Integer costItem = price * quantity;
                //Them vao ReceiptDetailDB
                ReceiptDetailDAO rdDAO = new ReceiptDetailDAO();
                rdDAO.CreateReceiptDetail(product_id, quantity, receipt_ID, price, costItem);
                
                //Cap nhat Inventory
                boolean is_lower_than_threshold = pDAO.AddQuantityToProduct(product_id, quantity );
                boolean isExisted = pdDAO.IsExistedInPendingList(product_id);
                
                
                if (is_lower_than_threshold) { //Neu so luong thap hon nguong
                    if (!isExisted){ //Chua ton tai trong Pending -> San pham moi tao, nhung import khong du
                        String notiMessage = "Tự động thêm lúc nhập hàng do không đủ số lượng";
                        pdDAO.CreatePendingList(product_id, import_date, notiMessage);
                    }
                } else { // Neu so luong cao hon nguong
                    if (isExisted){ // Ton tai trong pending -> Cap nhat trang thai is_resolved
                        pdDAO.UpdatePendingList(product_id);
                    }
                }
            }
            request.getSession().setAttribute("RECEIPT",null);
            Gson gson = new Gson();
            String receiptJSONString = gson.toJson(null);
            out.print(receiptJSONString);
            out.flush();
        }catch (SQLException e) {
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
