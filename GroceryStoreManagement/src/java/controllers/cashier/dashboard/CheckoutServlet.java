/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.customer.CustomerDAO;
import models.customer.CustomerDTO;
import models.pendingItem.PendingItemDAO;
import models.pendingItem.PendingItemDTO;
import models.product.ProductDAO;
import models.sessionBill.BillDAO;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;

/**
 *
 * @author Tran Minh Quan
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Integer getRatio() throws FileNotFoundException {
        try {
            ServletContext sc = getServletContext();
            String realPath = sc.getRealPath("/");
            String configPropertyFilePath = realPath + "WEB-INF/GSMconfig.properties";
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(configPropertyFilePath));
            return Integer.parseInt(appProps.getProperty("pointRatio"));
        } catch (Exception ex) {
            log("CheckoutServlet get Ratio exception: " + ex.getMessage());
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            BillObj billObj = (BillObj) request.getSession().getAttribute("BILL");

            String phone_no = billObj.getPhone_no();
            //Lay time hien tai
            Date date = new Date();
            Timestamp buy_date = new Timestamp(date.getTime());
            //lay ussername
            String cashier_username = (String) request.getSession().getAttribute("USERNAME");

            Integer total_cost = billObj.getTotal_cost();
            Integer point_used = 0;
            Integer point_gained = 0;
            Integer point_after = 0;
            if (billObj.isUse_point()) {
                point_used = Math.min((int) Math.ceil(billObj.getTotal_cost() / 1000),
                        billObj.getCustomer_dto().getPoint()); //???
            }
            if (billObj.getCustomer_dto() != null) {
                point_gained = (int) Math.floor((double) (total_cost / this.getRatio()));
                point_after = billObj.getCustomer_dto().getPoint() - point_used + point_gained;
            }

            //????????????? debug và sẽ xem xét
            Integer cash = request.getParameter("cash").length() > 0 ? Integer.parseInt(request.getParameter("cash")) : 0;

            ArrayList<BillItemObject> Bill_Detail = billObj.getBill_Detail();
            Integer profit = 0;
            for (BillItemObject detail : Bill_Detail) {
                System.out.println("Món :" + detail.getProduct().getName() + " có gia ban: " + detail.getProduct().getSelling_price() + " va gia goc: " + detail.getProduct().getCost_price());
                profit += (detail.getProduct().getSelling_price() - detail.getProduct().getCost_price()) * detail.getQuantity();
            }
            profit -= point_used * 1000;

            BillDAO bDAO = new BillDAO();
            //ghi bill xuống database
            Integer Bill_ID = bDAO.CreateBill(phone_no, buy_date,
                    cashier_username, total_cost, point_used, point_gained, point_after, cash, profit);
            PendingItemDAO ppDAO = new PendingItemDAO();
            ArrayList<PendingItemDTO> pendingList = ppDAO.GetPendingList();
            ProductDAO pDAO = new ProductDAO();

            CustomerDAO cDAO = new CustomerDAO();

            cDAO.AddPointCustomer(phone_no,
                    (int) Math.floor(total_cost / this.getRatio()) - point_used);

            for (BillItemObject detail : Bill_Detail) {

                Integer product_id = detail.getProduct().getProduct_ID();
                Integer quantity = detail.getQuantity();
                Integer cost = detail.getProduct().getSelling_price();
                Integer total = cost * quantity;
                //ghi bill detail xuống db
                bDAO.CreateBillDetail(Bill_ID, product_id, quantity, cost, total);

                //tìm xem đã có trong danh sách pending chưa
                boolean is_lower_than_threshold = pDAO.AddQuantityToProduct(product_id, (-1) * quantity);
                boolean already_in_pending = false;
                if (is_lower_than_threshold) {
                    for (int i = 0; i < pendingList.size(); i++) {
                        if (pendingList.get(i).getProduct_ID().equals(product_id)) {
                            already_in_pending = true;
                        }
                    }
                    if (already_in_pending == false) {
                        PendingItemDAO pendingDAO = new PendingItemDAO();
                        pendingDAO.CreatePendingList(product_id, buy_date, "Hết hàng - được thêm tự động");
                    }
                }
                //ghi vào pending
            }
            request.getSession().setAttribute("BILL", null);

            Gson gson = new Gson();
            String billJSONString = gson.toJson(null);
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
