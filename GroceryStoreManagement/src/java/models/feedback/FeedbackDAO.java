/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.feedback;

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
 * @author Tran Minh Quan
 */
public class FeedbackDAO implements Serializable {
        public boolean createFeedback(Timestamp feedback_date,
                String feedback_content, String cashier_username)
            throws SQLException,NamingException  {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                //B2. create SQL string 

                String sql = "INSERT INTO cashier_feedback "
                        + " (feedback_date, feedback_content,"
                        + " is_seen, cashier_username) "
                        + " VALUES (?,?,?,?)";

                stm = con.prepareStatement(sql);
                stm.setTimestamp(1, feedback_date);
                stm.setString(2, feedback_content);
                stm.setBoolean(3, false);
                stm.setString(4, cashier_username);
   
                int rowAffect = stm.executeUpdate();

                if (rowAffect == 1) {
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
