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
import models.product.ProductError;
import utils.TryParseInt;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddNewProductServlet", urlPatterns = {"/AddNewProductServlet"})
public class AddNewProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            boolean foundErr = false;
            ProductError err = new ProductError();
            ProductDAO dao = new ProductDAO();
            String productName;
            int productCategoryID, productLowerThreshold = 0, productCostPrice = 0, productSellingPrice = 0;
            String productUnitLabel, productLocation;
            boolean productIsSelling;
            if (TryParseInt.tryParse(request.getParameter("productLowerThreshold")) == null) {
                foundErr = true;
                err.setLowerThresholdErr("Mức dưới ngưỡng quá lớn");
            }
            if (TryParseInt.tryParse(request.getParameter("productCostPrice")) == null) {
                foundErr = true;
                err.setCostPriceErr("Tiền mua quá lớn");
            }
            if (TryParseInt.tryParse(request.getParameter("productSellingPrice")) == null) {
                foundErr = true;
                err.setSellingPriceErr("Tiền bán quá lớn");
            }
            if (!foundErr) {
                productLowerThreshold = Integer.parseInt(request.getParameter("productLowerThreshold"));
                productCostPrice = Integer.parseInt(request.getParameter("productCostPrice"));
                productSellingPrice = Integer.parseInt(request.getParameter("productSellingPrice"));
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
            }
            productName = request.getParameter("productName");
            productCategoryID = Integer.parseInt(request.getParameter("productCategoryID"));
            productUnitLabel = request.getParameter("productUnitLabel");
            productLocation = request.getParameter("productLocation");
            productIsSelling = request.getParameter("productIsSelling").equals("true");
            
            if (productLocation.length() > 15) {
                productLocation = productLocation.substring(0, 15);
            }
            if (productName.equals("") || productName.length() > 100) {
                foundErr = true;
                err.setNameErr("Tên món hàng phải từ 1 tới 100 chữ");
            }

            if (dao.ConfirmMatchedProduct(productName, 0)) {
                System.out.println(dao.ConfirmMatchedProduct(productName, 0));
                foundErr = true;
                err.setNameErr("Tên món hàng đã tồn tại");
            }
//            String productName = request.getParameter("productName");
//            int productCategoryID = Integer.parseInt(request.getParameter("productCategoryID"));
//            int productLowerThreshold = Integer.parseInt(request.getParameter("productLowerThreshold"));
//            int productCostPrice = Integer.parseInt(request.getParameter("productCostPrice"));
//            int productSellingPrice = Integer.parseInt(request.getParameter("productSellingPrice"));
//            String productUnitLabel = request.getParameter("productUnitLabel");
//            String productLocation = request.getParameter("productLocation");
//            boolean productIsSelling = request.getParameter("productIsSelling").equals("true");
//
//            if (productName.equals("") || productName.length() > 100) {
//                foundErr = true;
//                err.setNameErr("Tên món hàng phải từ 1 tới 100 chữ");
//            }
//
//            if (dao.ConfirmMatchedProduct(productName, 0)) {
//                foundErr = true;
//                err.setNameErr("Tên món hàng đã tồn tại");
//            }
//            if (productLowerThreshold < 0) {
//                foundErr = true;
//                err.setLowerThresholdErr("Mức dưới ngưỡng phải lớn hơn 0");
//            }
//            if (productCostPrice < 0) {
//                foundErr = true;
//                err.setCostPriceErr("Tiền mua phải lớn hơn 0");
//            }
//            if (productSellingPrice < 0) {
//                foundErr = true;
//                err.setSellingPriceErr("Tiền bán phải lớn hơn 0");
//            }

            Gson gson = new Gson();
            if (foundErr) {
                String jsonErr = gson.toJson(err);
                out.print(jsonErr);
            } else {
                boolean result = dao.AddNewProduct(productName, productCategoryID, productLowerThreshold, productCostPrice, productSellingPrice, productUnitLabel, productLocation, productIsSelling);
                if (result) {
                    String json = gson.toJson(null);
                    out.print(json);
                } else {
                    String json = gson.toJson("Something wrong");
                    out.print(json);
                }
            }

            out.flush();
        } catch (SQLException ex) {
            Logger.getLogger(AddNewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(AddNewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
