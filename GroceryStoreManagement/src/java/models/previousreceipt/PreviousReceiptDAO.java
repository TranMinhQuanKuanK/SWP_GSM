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
import java.util.ArrayList;
import javax.naming.NamingException;
import utils.DBHelpers;
import utils.StringNormalizer;

/**
 *
 * @author ROG STRIX
 */
public class PreviousReceiptDAO implements Serializable {

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
                    String import_date = rs.getString("import_date");
                    import_date = StringNormalizer.dateNormalize(import_date);
                    String store_owner_username = rs.getString("store_owner_username");
                    String owner_name = getStoreOwnerName(store_owner_username);
                    int total = rs.getInt("total");
                    PreviousReceiptDTO DTO = new PreviousReceiptDTO(receipt_ID, import_date, owner_name, total);
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

    public String getStoreOwnerName(String store_owner_username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT name "
                        + "FROM account "
                        + "WHERE username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, store_owner_username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                    return name;
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
        return name;
    }

    public ArrayList<PreviousReceiptDetailDTO> getReceiptDetails(int receiptID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<PreviousReceiptDetailDTO> result = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT R.product_ID, R.quantity, R.cost_price, R.total, P.name "
                        + "FROM receipt_detail R JOIN product P ON R.product_ID = P.product_ID "
                        + "WHERE R.receipt_ID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, receiptID);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int quantity = rs.getInt(2);
                    int cost = rs.getInt(3);
                    int total = rs.getInt(4);
                    String productName = rs.getString(5);

                    if (result == null) {
                        result = new ArrayList<>();
                    }

                    result.add(new PreviousReceiptDetailDTO(quantity, cost, total, productName));
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
}
