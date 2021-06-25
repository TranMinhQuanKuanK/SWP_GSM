package models.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utils.DBHelpers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tran Minh Quan + Huu Quoc
 */
public class AccountDAO implements Serializable {

    private List<AccountDTO> accountList;

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

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

    public boolean ChangePassword(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "UPDATE account "
                        + "SET password_acc = ?"
                        + " WHERE username = ? ";

                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, username);

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

    public void fetchAccountList()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT username, name, is_owner "
                        + "FROM account";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                while (rs.next()) {
                    String username = rs.getString("username");
                    String name = rs.getString("name");
                    boolean isOwner = rs.getBoolean("is_owner");

                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    }

                    this.accountList.add(new AccountDTO(username, name, isOwner));
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

    public boolean resetAccount(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "UPDATE account "
                        + "SET password_acc = '123456' "
                        + "WHERE username = ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                //4. Execute query
                int rowEffect = stm.executeUpdate();

                //5. Process Result
                if (rowEffect > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }

        return false;
    }

    public boolean deleteAccount(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();

            if (con != null) {
                //2. Create SQL string
                String sql = "DELETE FROM account "
                        + "WHERE username = ?";

                //3. Create statement and assign parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                //4. Execute query
                int rowEffect = stm.executeUpdate();

                //5. Process Result
                if (rowEffect > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }

        return false;
    }

    public boolean addAccount(String name, String username, String password, boolean isOwner)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect Database
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "INSERT INTO account (name, username, password_acc, is_owner) "
                        + "VALUES (?, ?, ?, ?)";

                //3. Create Statement and assign Parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, username);
                stm.setString(3, password);
                stm.setBoolean(4, isOwner);

                //4. Execute Query
                int rowEffect = stm.executeUpdate();

                //5. Process Result
                if (rowEffect > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return false;
    }

    public boolean checkExist(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect Database
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL string
                String sql = "SELECT username "
                        + "FROM account "
                        + "WHERE username = ?";

                //3. Create Statement and assign Parameter value if any
                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                //4. Execute query
                rs = stm.executeQuery();

                //5. Process result
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return false;
    }
}
