/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.receipt;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author ROG STRIX
 */
public class ReceiptDetailDAO implements Serializable{
    
    public boolean CreateReceiptDetail(Integer product_ID, Integer quantity, Integer receipt_ID, Integer cost_price, Integer total)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            String sql = "INSERT INTO receipt_detail "
                    + "(product_ID, quantity, receipt_ID, cost_price, total) "
                    + " VALUES (?,?,?,?,?)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, product_ID);
            stm.setInt(2, quantity);
            stm.setInt(3, receipt_ID);
            stm.setInt(4, cost_price);
            stm.setInt(5, total);
            int rowAffect = stm.executeUpdate();

            if (rowAffect > 0) {
                return true;
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
        return false;
    }
}
