/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.product;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.product.ProductDAO;
import models.product.ProductDTO;
import models.product.ProductError;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditProductInfoServlet", urlPatterns = {"/EditProductInfoServlet"})
public class EditProductInfoServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            boolean foundErr = false;
            ProductError err = new ProductError();
            ProductDAO dao = new ProductDAO();

            int productID = Integer.parseInt(request.getParameter("productID"));
            String productName = request.getParameter("productName");
            int productCategoryID = Integer.parseInt(request.getParameter("productCategoryID"));
            int productLowerThreshold = Integer.parseInt(request.getParameter("productLowerThreshold"));
            int productCostPrice = Integer.parseInt(request.getParameter("productCostPrice"));
            int productSellingPrice = Integer.parseInt(request.getParameter("productSellingPrice"));
            String productUnitLabel = request.getParameter("productUnitLabel");
            String productLocation = request.getParameter("productLocation");
            boolean productIsSelling = request.getParameter("productIsSelling").equals("true");

            if (productName.equals("") || productName.length() > 100) {
                foundErr = true;
                err.setNameErr("Tên món hàng phải từ 1 tới 100 chữ");
            }

            if (dao.ConfirmMatchedProduct(productName, productID)) {
                foundErr = true;
                err.setNameErr("Tên món hàng đã tồn tại");
            }
            if (productLowerThreshold < 0) {
                foundErr = true;
                err.setLowerThresholdErr("Mức dưới ngưỡng phải lớn hơn 0");
            }
            if (productCostPrice < 0) {
                foundErr = true;
                err.setCostPriceErr("Tiền mua phải lớn hơn 0");
            }
            if (productSellingPrice < 0) {
                foundErr = true;
                err.setSellingPriceErr("Tiền bán phải lớn hơn 0");
            }

            Gson gson = new Gson();
            if (foundErr) {
                String jsonErr = gson.toJson(err);
                out.print(jsonErr);
            } else {
                boolean result = dao.UpdateProductInfo(productID, productName, productCategoryID, productLowerThreshold, productCostPrice, productSellingPrice, productUnitLabel, productLocation, productIsSelling);
                if (result) {
                    String json = gson.toJson("");
                    out.print(json);
                } else {
                    String json = gson.toJson("Something wrong");
                    out.print(json);
                }
            }
            out.flush();
        } catch (SQLException ex) {
            Logger.getLogger(EditProductInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EditProductInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
