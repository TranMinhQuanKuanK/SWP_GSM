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
import models.receipt.ReceiptItem;
import models.receipt.ReceiptObj;

/**
 *
 * @author ROG STRIX
 */
@WebServlet(name = "AddToReceiptFromPendingServlet", urlPatterns = {"/AddToReceiptFromPendingServlet"})
public class AddToReceiptFromPendingServlet extends HttpServlet {

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
            ProductDAO pDAO = new ProductDAO();
            ProductDTO pDTO = pDAO.GetProductByID(product_ID);

            if (receipt == null) {
                session.setAttribute("RECEIPT", new ReceiptObj());
            } else {
                ArrayList<ReceiptItem> receipt_details = receipt.getReceipt_detail();
                //ktra san pham da ton tai tren session chua
                boolean found = false;
                for (int i = 0; i < receipt_details.size(); i++) {
                    if (receipt_details.get(i).getProduct().getProduct_ID() == product_ID) {
                        found = true;
                        break;
                    }
                }
                //Neu chua ton tai thi them vao session
                if (!found) {
                        ReceiptItem receiptItem = new ReceiptItem(pDTO, 1);
                        receipt.getReceipt_detail().add(receiptItem);
                        receipt.setTotal_cost(receipt.getTotal_cost() + pDTO.getSelling_price());
                }
                session.setAttribute("RECEIPT", receipt);
            }
            Gson gson = new Gson();
            String receiptJSONString = gson.toJson(receipt);
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
