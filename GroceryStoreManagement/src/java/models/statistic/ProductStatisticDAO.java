/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.statistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author Huu Quoc
 */
public class ProductStatisticDAO {

    private Map<Integer, StatisticObj> productStatisticMap;

    public Map<Integer, StatisticObj> getProductStatisticMap() {
        return productStatisticMap;
    }

    public void searchProductStatistic(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT customer_bill_detail.product_ID, customer_bill_detail.quantity, total, product.name "
                        + "FROM customer_bill_detail "
                        + "JOIN customer_bill ON customer_bill_detail.bill_ID = customer_bill.bill_ID "
                        + "JOIN product ON customer_bill_detail.product_ID = product.product_ID "
                        + "WHERE ? <= buy_date AND buy_date <= ?";
                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);

                //4. Execute query
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    int productID = rs.getInt("product_ID");
                    int quantity = rs.getInt("quantity");
                    int total = rs.getInt("total");
                    String productName = rs.getString("name");

                    if (this.productStatisticMap == null) {
                        this.productStatisticMap = new HashMap<>();
                    }

                    if (this.productStatisticMap.containsKey(productID)) {
                        quantity += this.productStatisticMap.get(productID).getQuantity();
                        total += this.productStatisticMap.get(productID).getTotal();
                    }
                    
                    this.productStatisticMap.put(productID, new StatisticObj(productName, quantity, total));
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
}
