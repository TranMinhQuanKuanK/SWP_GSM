package models.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utils.DBHelpers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tran Minh Quan
 */
public class AccountDAO implements Serializable {
      public AccountDTO CheckLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "SELECT username, name, is_owner "
                        + "FROM account "
                        + "WHERE username = ? AND password_acc = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);

                rs = stm.executeQuery();
                
                if (rs.next()) {
                    String name = rs.getString("name");
                    boolean is_owner = rs.getBoolean("is_owner");
                    AccountDTO aDTO = new AccountDTO(username, password, name, is_owner);
                    return aDTO;
                } else {
                    return null;
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
}
