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
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.statistic.FinancialStatisticDAO;
import models.statistic.FinancialStatisticObj;
import models.statistic.StatisticErrorObj;

/**
 *
 * @author Huu Quoc
 */
@WebServlet(name = "GetFinancialStatisticServlet", urlPatterns = {"/GetFinancialStatisticServlet"})
public class GetFinancialStatisticServlet extends HttpServlet {

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
            if (dateFrom.length() == 0 || dateTo.length() == 0) {
                errors.setIsError(true);
                errors.setDateError("Ngày nhập không tồn tại");
            } else if (dateFrom.compareTo(dateTo) > 0) {
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
                FinancialStatisticDAO dao = new FinancialStatisticDAO();
                FinancialStatisticObj result = new FinancialStatisticObj();

                if (dateFrom.length() == 7) {
                    dateFrom += "-01 00:00:00";
                }

                if (dateTo.length() == 7) {
                    dateTo = dao.nextMonth(dateTo);
                    dateTo += "-01 00:00:00";
                }

                result.setCountBill(dao.getBillCount(dateFrom, dateTo));
                result.setCountReceipt(dao.getReceiptCount(dateFrom, dateTo));
                result.setSumRevenue(dao.getSumRevenue(dateFrom, dateTo));
                result.setSumProfit(dao.getSumProfit(dateFrom, dateTo));
                result.setSumCost(dao.getSumCost(dateFrom, dateTo));

                Gson gson = new Gson();
                String financialStatisticJSONS = gson.toJson(result);
                out.print(financialStatisticJSONS);
                out.flush();
            }
        } catch (SQLException ex) {
            log("GetFinancialStatisticServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("GetFinancialStatisticServlet _ Naming: " + ex.getMessage());
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
