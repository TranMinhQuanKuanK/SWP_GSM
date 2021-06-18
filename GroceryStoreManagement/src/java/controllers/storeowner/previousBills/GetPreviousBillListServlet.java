/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.previousBills;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.previousBill.PreBillDAO;
import models.previousBill.PreBillDTO;
import models.previousBill.PreBillErrorObj;
import utils.StringNormalizer;

/**
 *
 * @author Huu Quoc
 */
@WebServlet(name = "GetPreviousBillListServlet", urlPatterns = {"/GetPreviousBillListServlet"})
public class GetPreviousBillListServlet extends HttpServlet {

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

        boolean foundErr = false;
        PreBillErrorObj errors = new PreBillErrorObj();
        String searchValue = request.getParameter("search-value");
        String dateFrom = request.getParameter("date-from").replace('T', ' ');
        String dateTo = request.getParameter("date-to").replace('T', ' ');

        if (searchValue == null) {
            searchValue = "";
        } else {
            searchValue = StringNormalizer.normalize(searchValue);
        }
        
        try (PrintWriter out = response.getWriter()) {
            //1. Check error
            if (dateFrom.compareTo(dateTo) > 0) {
                foundErr = true;
                errors.setDateError("End date must be greater than start date");
            }

            if (foundErr) {
                //2.1 Caching errors, forward to error page
                request.setAttribute("PREVIOUS_BILL_ERROR", errors);
            } else {
                //2.2 Call DAO
                PreBillDAO dao = new PreBillDAO();
                dao.searchPreviousBill(searchValue, dateFrom, dateTo);

                List<PreBillDTO> resultList = dao.getPreBillList();

                if (resultList == null) {
                    resultList = new ArrayList<>();
                }

                Gson gson = new Gson();
                String productStatisticJSONS = gson.toJson(resultList);
                out.print(productStatisticJSONS);
                out.flush();
            }
        } catch (SQLException ex) {
            log("GetPreviousBillListServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("GetPreviousBillListServlet _ Naming: " + ex.getMessage());
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
