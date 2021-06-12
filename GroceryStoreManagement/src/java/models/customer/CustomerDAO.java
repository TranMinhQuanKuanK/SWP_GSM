/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.customer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBHelpers;

/**
 *
 * @author Tran Minh Quan
 */
public class CustomerDAO implements Serializable {

    public CustomerDTO GetCustomerByPhone(String phone_no)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT name, point"
                        + " FROM customer "
                        + "WHERE phone_no = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, phone_no);
                rs = stm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    int point = rs.getInt("point");
                    CustomerDTO cDTO = new CustomerDTO(phone_no, name, point);
                    return cDTO;
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
        return null;
    }

    public boolean CreateNewCustomer(String phone_no, String name) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "INSERT INTO customer "
                        + " (phone_no, name, point)"
                        + " VALUES (?,?,?)";

                stm = con.prepareStatement(sql);
                stm.setString(1, phone_no);
                stm.setString(2, name);
                stm.setInt(3, 0);
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

    public boolean AddPointCustomer(String phone_no, Integer point) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT point"
                        + " FROM customer "
                        + "WHERE phone_no = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, phone_no);
                rs = stm.executeQuery();

                Integer current_point = rs.getInt(1);

                //--------------------------------
                sql = "UPDATE customer "
                        + "SET point = ?"
                        + " WHERE phone_no = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, current_point + point);
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
