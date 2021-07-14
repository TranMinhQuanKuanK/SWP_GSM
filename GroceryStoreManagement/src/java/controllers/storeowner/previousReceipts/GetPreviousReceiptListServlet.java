/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.previousReceipts;

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
import models.previousreceipt.PreviousReceiptDAO;
import models.previousreceipt.PreviousReceiptDTO;

/**
 *
 * @author ROG STRIX
 */
@WebServlet(name = "GetPreviousReceiptListServlet", urlPatterns = {"/GetPreviousReceiptListServlet"})
public class GetPreviousReceiptListServlet extends HttpServlet {

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
            String dateFrom = request.getParameter("date-from");
            String dateTo = request.getParameter("date-to");
            if (dateFrom.length() == 10) {
                dateFrom += " 00:00:00";
            }
            if (dateTo.length() == 10) {
                dateTo += " 23:59:59";
            }
            PreviousReceiptDAO DAO = new PreviousReceiptDAO();
            ArrayList<PreviousReceiptDTO> receiptList = DAO.GetPreviousReceipt(dateFrom, dateTo);
            Gson gson = new Gson();
            String productJSONString = gson.toJson(receiptList);
            out.print(productJSONString);
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
