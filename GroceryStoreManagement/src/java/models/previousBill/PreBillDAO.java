/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.previousBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utils.DBHelpers;
import utils.StringNormalizer;

/**
 *
 * @author Huu Quoc
 */
public class PreBillDAO {

    private List<PreBillDTO> preBillList;

    public List<PreBillDTO> getPreBillList() {
        return preBillList;
    }

    public void searchPreviousBill(String searchValue, String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT bill_ID, name, customer.phone_no, buy_date, total_cost, "
                           + "cashier_username, point_used, cash "
                           + "FROM customer_bill "
                           + "JOIN customer ON customer.phone_no = customer_bill.phone_no "
                           + "WHERE ? <= buy_date AND buy_date <= ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    int billID = rs.getInt("bill_ID");
                    String name = rs.getString("name");
                    String phoneNo = rs.getString("phone_no");
                    String buyDate = rs.getString("buy_date");
                    int totalCost = rs.getInt("total_cost");
                    List<PreBillDetailDTO> details = getBillDetails(billID);
                    String cashier = getCashierName(rs.getString("cashier_username"));
                    int pointUsed = rs.getInt("point_used");
                    int cash = rs.getInt("cash");

                    if (searchValue.length() == 0 || Character.isDigit(searchValue.charAt(0))) {
                        if (!phoneNo.contains(searchValue)) {
                            continue;
                        }
                    } else {
                        if (!StringNormalizer.normalize(name).contains(searchValue)) {
                            continue;
                        }
                    }

                    if (this.preBillList == null) {
                        this.preBillList = new ArrayList<>();
                    }

                    this.preBillList.add(new PreBillDTO(billID, totalCost, pointUsed, cash,
                            name, phoneNo, buyDate, cashier, details));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public void searchGuestPreviousBill(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT bill_ID, buy_date, total_cost, "
                           + "cashier_username, point_used, cash "
                           + "FROM customer_bill "
                           + "WHERE customer_bill.phone_no IS NULL AND "
                           + "? <= buy_date AND buy_date <= ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    int billID = rs.getInt("bill_ID");
                    String name = "...";
                    String phoneNo = "...";
                    String buyDate = rs.getString("buy_date");
                    int totalCost = rs.getInt("total_cost");
                    List<PreBillDetailDTO> details = getBillDetails(billID);
                    String cashier = getCashierName(rs.getString("cashier_username"));
                    int pointUsed = rs.getInt("point_used");
                    int cash = rs.getInt("cash");

                    if (this.preBillList == null) {
                        this.preBillList = new ArrayList<>();
                    }

                    this.preBillList.add(new PreBillDTO(billID, totalCost, pointUsed, cash,
                            name, phoneNo, buyDate, cashier, details));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public List<PreBillDetailDTO> getBillDetails(int billID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<PreBillDetailDTO> result = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT product_ID, quantity, cost, total "
                           + "FROM customer_bill_detail "
                           + "WHERE bill_ID = ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setInt(1, billID);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    int cost = rs.getInt("cost");
                    int total = rs.getInt("total");
                    String productName = getProductName(rs.getInt("product_ID"));

                    if (result == null) {
                        result = new ArrayList<>();
                    }

                    result.add(new PreBillDetailDTO(quantity, cost, total, productName));
                }
            }

            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public String getCashierName(String cashierUsername)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String cashierName = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT name "
                           + "FROM account "
                           + "WHERE username = ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, cashierUsername);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    cashierName = rs.getString("name");
                }
            }

            return cashierName;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public String getProductName(int productID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String productName = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT name\n"
                           + "FROM product\n"
                           + "WHERE product_ID = ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setInt(1, productID);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    productName = rs.getString("name");
                }
            }

            return productName;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
