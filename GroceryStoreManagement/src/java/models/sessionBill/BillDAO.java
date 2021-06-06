package models.sessionBill;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import utils.DBHelpers;

public class BillDAO implements Serializable {

    //để checkout
    public Integer CreateBill(String phone_no, Timestamp buy_date, String cashier_username,
            Integer total_cost, Integer point_used, Integer cash, Integer profit)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Integer billID = null;
        try {
            con = DBHelpers.makeConnection();
            String sql = "INSERT INTO customer_bill(phone_no,"
                    + "buy_date, cashier_username, total_cost, "
                    + "point_used , cash, profit)"
                    + " VALUES (?,?,?,?,?,?,?)";

            stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, phone_no);
            stm.setTimestamp(2, buy_date);
            stm.setString(3, cashier_username);
            stm.setInt(4, total_cost);
            stm.setInt(5, point_used);
            stm.setInt(6, cash);
            stm.setInt(7, profit);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                billID = rs.getInt(1);
            }
            return billID;
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

    public boolean CreateBillDetail(Integer bill_ID, Integer product_ID,
            Integer quantity, Integer cost, Integer total)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            String sql = "INSERT INTO customer_bill_detail"
                    + "(bill_ID, product_ID, quantity, cost,total)"
                    + " VALUES (?,?,?,?,?)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, bill_ID);
            stm.setInt(2, product_ID);
            stm.setInt(3, quantity);
            stm.setInt(4, cost);
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
