/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashier.dashboard;

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
import models.category.CategoryDAO;
import models.category.CategoryDTO;
import models.product.ProductDAO;
import models.product.ProductDTO;
import models.sessionBill.BillErrObj;
import models.sessionBill.BillItemObject;
import models.sessionBill.BillObj;
//chưa test

@WebServlet(name = "AddProducToBillServlet", urlPatterns = {"/AddProducToBillServlet"})
public class AddProducToBillServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("I went to product_to_bill servlet");
            HttpSession session = request.getSession();
            Integer product_id = Integer.parseInt(request.getParameter("product_id"));
            System.out.println("product id toi nhan la: " + product_id);
            BillObj bill = (BillObj) session.getAttribute("BILL");
            ProductDAO pDAO = new ProductDAO();
            ProductDTO pDTO = pDAO.GetProductByID(product_id);

            if (bill == null) {
                session.setAttribute("BILL", new BillObj());
            } else {
                ArrayList<BillItemObject> details = bill.getBill_Detail();

//                for (BillItemObject b : details) {
//                    if (b.getProduct().getProduct_ID() == product_id) {
//                        found = true;
//                        b.setQuantity(b.getQuantity() + 1);
//                    }
//                }
                //Tìm trong bill trên session xem sản phẩm đó đã có chưa
                boolean found = false;
                int found_index = -1;
                for (int i = 0; i < details.size(); i++) {
                    if (details.get(i).getProduct().getProduct_ID() == product_id) {
                        found = true;
                        found_index = i;
                        //details.get(i).setQuantity(details.get(i).getQuantity() + 1);
                    }
                }
                //Nếu tồn tại trong detail thì xét số lượng, nếu lớn hơn số lượng dưới db thì lỗi
                if (found) {
                    int currentQuantityOnBill = details.get(found_index).getQuantity();
                    if ((currentQuantityOnBill + 1) <= pDTO.getQuantity()) {
                        details.get(found_index).setQuantity(currentQuantityOnBill + 1);
                        //set lại error lỗi là không có lỗi;
                        bill.setErr_obj(new BillErrObj());
                    } else {
                        //Set lỗi
                        BillErrObj bill_error = new BillErrObj();
                        bill_error.appendError("\"" + pDTO.getName() + "\" chỉ còn " + pDTO.getQuantity() + " sản phẩm trong kho");
                        bill_error.setHasError(true);
                        bill.setErr_obj(bill_error);
                    };
                }
                //Nếu không tồn tại trong detail thì xét nếu db còn <2 sản phẩm thì lỗi
                if (!found) {
                    if (pDTO.getQuantity() > 0) {
                        BillItemObject billItem = new BillItemObject(pDTO, 1);
                        bill.getBill_Detail().add(billItem);
                        bill.setErr_obj(new BillErrObj());
                    } else  {
                        //Set lỗi
                        BillErrObj bill_error = new BillErrObj();
                        bill_error.appendError("\"" + pDTO.getName() + "\" chỉ còn " + pDTO.getQuantity() + " sản phẩm trong kho");
                        bill_error.setHasError(true);
                        bill.setErr_obj(bill_error);
                    };
                }
                bill.setTotal_cost(bill.getTotal_cost() + pDTO.getSelling_price());

                session.setAttribute("BILL", bill);

            }
            Gson gson = new Gson();
            String billJSONString = gson.toJson(bill);
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
