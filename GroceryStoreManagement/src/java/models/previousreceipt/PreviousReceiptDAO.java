/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.previousreceipt;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author ROG STRIX
 */
public class PreviousReceiptDAO implements Serializable{
    
    private ArrayList<PreviousReceiptDTO> listReceipt = new ArrayList<>();
    
    public ArrayList<PreviousReceiptDTO> GetPreviousReceipt(String dateFrom, String dateTo)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT receipt_ID, import_date, store_owner_username, total "
                        + "FROM receipt "
                        + "WHERE ? <= import_date AND import_date <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dateFrom);
                stm.setString(2, dateTo);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int receipt_ID = rs.getInt("receipt_ID");
                    Timestamp import_date = rs.getTimestamp("import_date");
                    String store_owner_username = rs.getString("store_owner_username");
                    int total = rs.getInt("total");
                    PreviousReceiptDTO DTO = new PreviousReceiptDTO(receipt_ID, import_date, store_owner_username, total);
                    listReceipt.add(DTO);
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
        return listReceipt;
    }
}
