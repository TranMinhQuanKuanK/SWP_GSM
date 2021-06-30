/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.storeowner.inventory;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.pendingItem.PendingItemDAO;

/**
 *
 * @author ROG STRIX
 */
@WebServlet(name = "AddToSuggestionServlet", urlPatterns = {"/AddToSuggestionServlet"})
public class AddToSuggestionServlet extends HttpServlet {

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
            int productID = Integer.parseInt(request.getParameter("product_ID"));
            String notiMessage = request.getParameter("noti_mess");
            if (notiMessage.equals("owner")) {
                notiMessage = "Được thêm bởi store owner";
                Date date = new Date();
                Timestamp noteday = new Timestamp(date.getTime());
                PendingItemDAO DAO = new PendingItemDAO();
                boolean isExisted = DAO.IsExistedInPendingList(productID);
                Gson gson = new Gson();
                if (!isExisted) { // chưa tồn tại trong Pending thì ghi xuống
                    DAO.CreatePendingList(productID, noteday, notiMessage);
                    String notiJSONString = gson.toJson("1");
                    out.print(notiJSONString);
                    out.flush();
                } else {
                    String notiJSONString = gson.toJson("1");
                    out.print(notiJSONString);
                    out.flush();
                }
            } else if (notiMessage.equals("auto")){
                notiMessage = "Được thêm tự động";
                Date date = new Date();
                Timestamp noteday = new Timestamp(date.getTime());
                PendingItemDAO DAO = new PendingItemDAO();
                boolean isExisted = DAO.IsExistedInPendingList(productID);
                Gson gson = new Gson();
                if (!isExisted) { 
                    DAO.CreatePendingList(productID, noteday, notiMessage);
                    String notiJSONString = gson.toJson("1");
                    out.print(notiJSONString);
                    out.flush();
                } 
            }

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
