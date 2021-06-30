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
import java.sql.Timestamp;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author ROG STRIX
 */
public class ReceiptDAO implements Serializable {

    public Integer CreateReceipt(Timestamp import_date, String username, Integer total)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Integer receiptID = null;
        try {
            con = DBHelpers.makeConnection();
            String sql = "INSERT INTO receipt(import_date, store_owner_username, total) "
                    + " VALUES (?,?,?)";

            stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setTimestamp(1, import_date);
            stm.setString(2, username);
            stm.setInt(3, total);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                receiptID = rs.getInt(1);
            }
            return receiptID;
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
