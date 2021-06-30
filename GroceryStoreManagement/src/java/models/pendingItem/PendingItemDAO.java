/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.pendingItem;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.NamingException;
import models.category.CategoryDAO;
import models.category.CategoryDTO;
import models.product.ProductDTO;
import utils.DBHelpers;
import utils.StringNormalizer;

/**
 *
 * @author quan6
 */
public class PendingItemDAO implements Serializable {

    private ArrayList<PendingItemDTO> listPendingNoti = new ArrayList<>();
    private ArrayList<SuggestionListDTO> suggestionList = new ArrayList<>();

    public ArrayList<PendingItemDTO> GetPendingList()
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT pending_ID, product_ID, pending_date"
                        + ",is_resolved,note "
                        + " FROM pending_product_noti WHERE is_resolved = ?";

                stm = con.prepareStatement(sql);
                stm.setBoolean(1, false);
                rs = stm.executeQuery();
                StringNormalizer norm = new StringNormalizer();
                while (rs.next()) {
                    int pending_ID = rs.getInt("pending_ID");
                    int product_ID = rs.getInt("product_ID");
                    Timestamp pending_date = rs.getTimestamp("pending_date");
                    String note = rs.getString("note");

                    PendingItemDTO pDTO = new PendingItemDTO(pending_ID,
                            product_ID, pending_date, false, note);
                    listPendingNoti.add(pDTO);

                }
                return listPendingNoti;
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

        return null;
    }
    
    public ArrayList<SuggestionListDTO> GetSuggestionList()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT Pro.product_ID, Pro.name, Pro.quantity, Pe.is_resolved "
                        + "FROM pending_product_noti Pe INNER JOIN product Pro ON Pe.product_ID = Pro.product_ID "
                        + "WHERE Pe.is_resolved = ?";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, false);
                rs = stm.executeQuery();
                StringNormalizer norm = new StringNormalizer();
                while (rs.next()) {
                    int product_ID = rs.getInt("product_ID");
                    String product_name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    SuggestionListDTO DTO = new SuggestionListDTO(product_ID, product_name, quantity);
                    suggestionList.add(DTO);
                }
                return suggestionList;
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

        return null;
    }

    public boolean IsExistedInPendingList(Integer productID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT product_ID "
                        + "FROM pending_product_noti "
                        + "WHERE product_ID = ? AND is_resolved = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productID);
                stm.setBoolean(2, false);
                rs = stm.executeQuery();
                if (rs.next()) {
                   return true;
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
        return false;
    }
   

    public boolean CreatePendingList(Integer productID, Timestamp pending_date, String note) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "INSERT INTO pending_product_noti "
                        + " (product_ID, pending_date, is_resolved,note)"
                        + " VALUES (?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productID);
                stm.setTimestamp(2, pending_date);
                stm.setBoolean(3, false);
                stm.setString(4, note);

                int rowAffect = stm.executeUpdate();
                if (rowAffect > 0) {
                    return true;
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
        return false;
    }
    
    public boolean UpdatePendingList(Integer productID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "UPDATE pending_product_noti\n"
                        + "SET is_resolved = ?\n"
                        + "WHERE product_ID = ? and is_resolved = ?";

                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, productID);
                stm.setBoolean(3, false);
                int rowAffect = stm.executeUpdate();
                if (rowAffect > 0) {
                    return true;
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
        return false;
    }
}
