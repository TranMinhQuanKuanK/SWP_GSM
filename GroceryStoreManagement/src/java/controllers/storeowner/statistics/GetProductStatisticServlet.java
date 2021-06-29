/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.statistics;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.statistic.ProductStatisticDAO;
import models.statistic.ProductStatisticDTO;
import models.statistic.StatisticErrorObj;

/**
 *
 * @author Huu Quoc
 */
@WebServlet(name = "GetProductStatisticServlet", urlPatterns = {"/GetProductStatisticServlet"})
public class GetProductStatisticServlet extends HttpServlet {

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
        
        StatisticErrorObj errors = new StatisticErrorObj();
        String dateFrom = request.getParameter("date-from").replace('T', ' ');
        String dateTo = request.getParameter("date-to").replace('T', ' ');

        try (PrintWriter out = response.getWriter()) {
            //1. Check error
            if (dateFrom.compareTo(dateTo) > 0) {
                errors.setIsError(true);
                errors.setDateError("Ngày kết thúc phải lớn hơn ngày bắt đầu");
            }

            if (errors.isIsError()) {
                //2.1 Caching errors, return error object
                Gson gson = new Gson();
                String errorJSONS = gson.toJson(errors);
                out.print(errorJSONS);
                out.flush();
            } else {
                //2.2 Call DAO
                ProductStatisticDAO dao = new ProductStatisticDAO();
                dao.searchProductStatistic(dateFrom, dateTo);

                Map<Integer, ProductStatisticDTO> resultMap = dao.getProductStatisticMap();
                List<ProductStatisticDTO> resultList = new ArrayList<>();
                
                if (resultMap != null) {
                    resultList = new ArrayList<>(resultMap.values());
                    Collections.sort(resultList, Comparator.comparing(ProductStatisticDTO::getQuantity).reversed());
                }

                Gson gson = new Gson();
                String productStatisticJSONS = gson.toJson(resultList);
                out.print(productStatisticJSONS);
                out.flush();
            }
        } catch (SQLException ex) {
            log("GetProductStatisticServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("GetProductStatisticServlet _ Naming: " + ex.getMessage());
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
