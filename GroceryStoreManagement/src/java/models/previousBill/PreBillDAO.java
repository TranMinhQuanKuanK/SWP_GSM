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
                String searchCriterion;
                if (searchValue.length() == 0 || Character.isDigit(searchValue.charAt(0))) {
                    searchCriterion = "customer.phone_no";
                } else {
                    searchCriterion = "name";
                }
                
                String sql = "SELECT bill_ID, name, customer.phone_no, buy_date, total_cost " +
                             "FROM customer_bill " +
                             "JOIN customer ON customer.phone_no = customer_bill.phone_no " +   
                             "WHERE " + searchCriterion + " LIKE ? AND ? <= buy_date AND buy_date <= ?";
                
                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setString(2, dateFrom);
                stm.setString(3, dateTo);
                
                //4. Execute query
                rs = stm.executeQuery();
                
                //5. Process result
                while (rs.next()) {
                    int billID = rs.getInt("bill_ID");
                    String name = rs.getString("name");
                    String phoneNo = rs.getString("phone_no");
                    String buyDate = rs.getString("buy_date");
                    int totalCost = rs.getInt("total_cost");
                    
                    buyDate = StringNormalizer.dateNormalize(buyDate);
                    String totalCostString = StringNormalizer.moneyNormalize(Integer.toString(totalCost));

                    if (this.preBillList == null) {
                        this.preBillList = new ArrayList<>();
                    }

                    this.preBillList.add(new PreBillDTO(billID, name, phoneNo, buyDate, totalCostString));
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
