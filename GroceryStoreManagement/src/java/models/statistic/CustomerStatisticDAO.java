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
public class CustomerStatisticDAO {

    private Map<String, CustomerStatisticDTO> customerStatisticMap;

    public Map<String, CustomerStatisticDTO> getCustomerStatisticMap() {
        return customerStatisticMap;
    }

    public void searchCustomerStatistic(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT customer.phone_no, total_cost, name "
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
                    int total = rs.getInt("total_cost");
                    String phoneNum = rs.getString("phone_no");
                    String customerName = rs.getString("name");
                    int quantity = 1;

                    if (this.customerStatisticMap == null) {
                        this.customerStatisticMap = new HashMap<>();
                    }

                    if (this.customerStatisticMap.containsKey(phoneNum)) {
                        quantity += this.customerStatisticMap.get(phoneNum).getQuantity();
                        total += this.customerStatisticMap.get(phoneNum).getTotal();
                    }

                    this.customerStatisticMap.put(phoneNum,
                            new CustomerStatisticDTO(quantity, total, phoneNum, customerName));
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
